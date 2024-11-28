import mysql.connector # type: ignore
import requests # type: ignore
from bs4 import BeautifulSoup

# Diccionario que asigna un valor numérico a cada ronda
rondas = {"R16": 0, "R8": 1, "R4": 2, "Semi": 3, "Final": 4}

# Función para extraer la ubicación de un torneo dado su ID
def extract_tournament_data(torneo_Id):
    url = f"https://www.a1padelglobal.com/torneoPartidos.aspx?idTorneo={torneo_Id}"
    response = requests.get(url)
    soup = BeautifulSoup(response.text, 'html.parser')

    # Encuentra todos los títulos "Cuadro"
    cuadro_titles = soup.find_all('p', class_='table-2__title')

    ciudad = "Ubicación no encontrada."
    tipo = "Nombre no encontrado."
    fechatorneo = "Nombre no encontrado."
    all_tournament_data = []

    for cuadro_title in cuadro_titles:
        # Encuentra el siguiente <div class="table-2__container"> después del título "Cuadro"
        cuadro_container = cuadro_title.find_next_sibling('div', class_='table-2__container')

        if cuadro_container:
            # Encuentra todos los partidos dentro del contenedor del cuadro
            partidos = cuadro_container.find_all('div', class_='row table-2__rowd')

            # Eliminar partidos que no tienen información
            partidos = [partido for partido in partidos if partido.find(class_='col table-2__col col-1') is not None]

            tournament_data = []

            for partido in partidos:
                fecha_hora = partido.find(class_='col table-2__col col-2').text.strip()
                ronda = partido.find(class_='col table-2__col col-1').text.strip()

                # Extraemos las parejas
                parejas = partido.find_all(class_='col-35')

                pareja1_names = []
                pareja2_names = []

                for pareja in parejas:
                    names = pareja.find_all('p', class_='table-2__name')
                    if names:
                        if not pareja1_names:
                            pareja1_names = names
                        else:
                            pareja2_names = names
                            break

                pareja1 = ', '.join([p.text.strip() for p in pareja1_names])
                pareja2 = ', '.join([p.text.strip() for p in pareja2_names])

                # Verificar si el resultado está presente
                resultado_element = partido.find_all(class_='col table-2__col col-2')[-1]
                resultado = resultado_element.text.strip() if resultado_element is not None else "0/0/0"

                # Agregamos la información del partido a la lista de datos del torneo
                tournament_data.append({
                    "Ronda": ronda,
                    "Fecha y Hora": fecha_hora,
                    "Pareja 1": pareja1,
                    "Pareja 2": pareja2,
                    "Resultado": resultado
                })

            all_tournament_data.append(tournament_data)

    # Buscar información de ubicación y tipo de torneo (ciudad, tipo, fechatorneo)
    localizacion_ciudad = soup.find('p', class_='master__location')
    localizacion_tipo = soup.find('span', class_ ='master__title2')
    localizacion_fechatorneo = soup.find('p', class_ ='master__date')

    if localizacion_ciudad:
        ciudad = localizacion_ciudad.get_text(strip=True)

    if localizacion_tipo:
        tipo = localizacion_tipo.get_text(strip=True)

    if localizacion_fechatorneo:
        fechatorneo = localizacion_fechatorneo.get_text(strip=True)

    return ciudad, tipo, fechatorneo, all_tournament_data


# Función para insertar los datos en la base de datos MySQL
def insert_data_into_mysql(torneo_Id, ciudad, tipo, fechatorneo, all_tournament_data):
    # Establecer la conexión a la base de datos
    connection = mysql.connector.connect(
         host="resultadosmaster-mysql-server.mysql.database.azure.com",
        port="3306",
        user="resultadosmaster",
        password="Tolito1234",
        database="resultadosmaster"
    )

    cursor = connection.cursor()

    # Verificar si el torneo ya existe en la tabla 'torneos'
    cursor.execute("SELECT * FROM torneos WHERE id = %s", (torneo_Id,))
    existing_tournament = cursor.fetchone()

    if existing_tournament:
        print(f"El torneo con ID {torneo_Id} ya existe en la base de datos. No se realizará la inserción.")
        return

    # Insertar la ubicación del torneo en la tabla 'torneos'
    cursor.execute("INSERT INTO torneos (id, ciudad, tipo, fechatorneo) VALUES (%s, %s, %s, %s)", (torneo_Id, ciudad, tipo, fechatorneo))
    connection.commit()

    # Insertar los datos de los partidos en la tabla 'partidos'
    for tournament_data in all_tournament_data:
        for partido in tournament_data:
            cursor.execute("""
                INSERT INTO partidos (torneo_Id, ronda, fecha_hora, pareja_1, pareja_2, resultado)
                VALUES (%s, %s, %s, %s, %s, %s)
            """, (torneo_Id, partido["Ronda"], partido["Fecha y Hora"], partido["Pareja 1"], partido["Pareja 2"], partido["Resultado"]))
            connection.commit()

    # Cerrar la conexión
    cursor.close()
    connection.close()


# Iterar sobre los IDs de torneos en el rango de 2000 a 2500
for torneo_Id in range(2300, 2350):
    ciudad, tipo, fechatorneo, all_tournament_data = extract_tournament_data(torneo_Id)
    print(f"Torneo {torneo_Id} - Ciudad: {ciudad}- Tipo: {tipo}- Fecha torneo: {fechatorneo}")
    if isinstance(all_tournament_data, list):
        for tournament_data in all_tournament_data:
            for partido in tournament_data:
                print("Ronda:", partido["Ronda"])
                print("Fecha y Hora:", partido["Fecha y Hora"])
                print("Pareja 1:", partido["Pareja 1"])
                print("Pareja 2:", partido["Pareja 2"])
                print("Resultado:", partido["Resultado"])
                print()
        # Verificar si se encontró la ubicación y el nombre del torneo
        if ciudad != "Ubicación no encontrada." and tipo != "Torneo no encontrado.":
            # Insertar los datos en la base de datos MySQL
            insert_data_into_mysql(torneo_Id, ciudad, tipo, fechatorneo, all_tournament_data)
        else:
            print("La ubicación o el nombre del torneo no fueron encontrados. No se realizará la inserción en la base de datos.")
    else:
        print(all_tournament_data)
    print()

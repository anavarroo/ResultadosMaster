import mysql.connector
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from urllib.parse import urljoin
import time

def scrape_players_by_gender(url, gender, driver):
    # Realizar la solicitud GET a la URL
    driver.get(url)
    
    # Esperar a que el elemento <select> esté presente
    dropdown = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, 'tipoRanking')))

    # Seleccionar el género deseado en el elemento <select>
    dropdown.click()
    option_xpath = f'//option[@value="{gender}"]'
    gender_option = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, option_xpath)))
    gender_option.click()

    # Esperar un momento para que la página se cargue completamente después de seleccionar la opción
    time.sleep(2)

    # Parsear el contenido HTML con BeautifulSoup
    soup = BeautifulSoup(driver.page_source, 'html.parser')
    
    # Encontrar todos los elementos con la clase "player-card"
    player_cards = soup.find_all('div', class_='player-card')
    
    # Inicializar una lista para almacenar los datos de cada jugador
    players_data = []
    
    # Iterar sobre cada player card
    for card in player_cards:
        # Extraer los datos que necesitas de cada player card
        first_name = card.find('span', class_='f-name').text.strip()
        last_name = card.find('h3', class_='l-name').text.strip()
        ranking_number = card.find('span', class_='right-col ranking-number').text.strip()
        player_score = card.find('span', class_='player-score').text.strip()
        
        # Obtener la URL completa de la imagen del jugador
        image_url = card.find('img', class_='player-image')['src']
        full_image_url = urljoin(url, image_url)
        
        # Obtener la URL completa de la bandera del jugador
        flag_url = card.find('img', class_='player-country')['src']
        full_flag_url = urljoin(url, flag_url)
   
        # Agregar los datos del jugador a la lista
        players_data.append((first_name, last_name, ranking_number, player_score, full_image_url, full_flag_url, gender))

    return players_data

# URL de la página con las player cards
url = "https://www.a1padelglobal.com/rankinga1p.aspx"

# Configuración del navegador
options = webdriver.ChromeOptions()
options.add_argument('--headless')  # Modo sin cabeza
driver = webdriver.Chrome(options=options)  # Puedes cambiar a Firefox, etc.

# Conectar a la base de datos MySQL
try:
    
    connection = mysql.connector.connect(
        host="resultadosmaster-mysql-server.mysql.database.azure.com",
        port="3306",
        user="resultadosmaster",
        password="Tolito1234",
       database="top50players"
     )

   # connection = mysql.connector.connect(
   #     host="localhost",
   #     port="3306",
   #     user="root",
   #     password="root",
   #     database="top50players"
   # )
    
    if connection.is_connected():
        cursor = connection.cursor()

        # Realizar scraping para el ranking masculino
        jugadores_masculinos = scrape_players_by_gender(url, 'MASCULINO', driver)

        # Insertar los datos en la tabla Jugadores
        for jugador in jugadores_masculinos:
            # Verificar si el jugador ya existe en la base de datos
            query = "SELECT id FROM Jugadores WHERE nombre = %s AND apellidos = %s AND numero_ranking = %s AND genero = %s"
            cursor.execute(query, (jugador[0], jugador[1], jugador[2], jugador[6]))
            existing_player = cursor.fetchone()

            if existing_player:
                print(f"El jugador masculino ya existe en la base de datos. No se realizará la inserción.")
                continue

            # Si el jugador no existe, proceder con la inserción
            query = "INSERT INTO Jugadores (nombre, apellidos, numero_ranking, puntos, imagen_url, bandera_url, genero) VALUES (%s, %s, %s, %s, %s, %s, %s)"
            cursor.execute(query, (jugador[0], jugador[1], jugador[2], jugador[3], jugador[4], jugador[5], jugador[6]))

        # Realizar scraping para el ranking femenino
        jugadores_femeninos = scrape_players_by_gender(url, 'FEMENINO', driver)

        # Insertar los datos en la tabla Jugadores
        for jugador in jugadores_femeninos:
            # Verificar si el jugador ya existe en la base de datos
            query = "SELECT id FROM Jugadores WHERE nombre = %s AND apellidos = %s AND numero_ranking = %s AND genero = %s"
            cursor.execute(query, (jugador[0], jugador[1], jugador[2], jugador[6]))
            existing_player = cursor.fetchone()

            if existing_player:
                print(f"El jugador femenino ya existe en la base de datos. No se realizará la inserción.")
                continue

            # Si el jugador no existe, proceder con la inserción
            query = "INSERT INTO Jugadores (nombre, apellidos, numero_ranking, puntos, imagen_url, bandera_url, genero) VALUES (%s, %s, %s, %s, %s, %s, %s)"
            cursor.execute(query, (jugador[0], jugador[1], jugador[2], jugador[3], jugador[4], jugador[5], jugador[6]))

        # Confirmar la transacción y cerrar la conexión
        connection.commit()
        cursor.close()
        connection.close()
        print("Inserción exitosa en la base de datos.")
    else:
        print("No se pudo conectar a la base de datos.")

except mysql.connector.Error as error:
    print("Error al conectar a la base de datos:", error)

# Cerrar el navegador después de completar todas las tareas
driver.quit()
    
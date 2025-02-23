import requests

def get_weather(city):
    api_key = "26060d6ca4e04d2489a163047252202"
    url = f"https://api.weatherapi.com/v1/current.json?key={api_key}&q={city}"
    
    try:
        response = requests.get(url)
        data = response.json()
        
        if "error" in data:
            print("Error:", data["error"]["message"])
        else:
            print(f"City: {data['location']['name']}, {data['location']['country']}")
            print(f"Temperature: {data['current']['temp_c']}°C")
            print(f"Condition: {data['current']['condition']['text']}")
    except Exception as e:
        print("Failed to fetch weather data:", e)

if __name__ == "__main__":
    city = input("Enter city name: ")
    get_weather(city)

#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <Servo.h>
#include <OneWire.h>
#include <DallasTemperature.h>

// WiFi CREDENTIALS
const char *ssid = "Bibiana_2G";
const char *password = "Bi7biana";

// Pins
#define PIN_TRIG D3
#define PIN_ECHO D2
#define PIN_SERVO D8
#define PIN_DS18B20 D7

// Ultrasonic Sensor
float porcentaje = 0;

// Web Server
ESP8266WebServer server(80);

// Servo
Servo miServo;
bool servoEnMovimiento = false;

// Temperature Sensor
OneWire oneWire(PIN_DS18B20);
DallasTemperature sensors(&oneWire);

unsigned long lastTempMillis = 0;
const unsigned long tempInterval = 10000; // Intervalo para leer la temperatura

void setup() {
  Serial.begin(115200);
  conectarWifi();
  pinMode(PIN_TRIG, OUTPUT);
  pinMode(PIN_ECHO, INPUT);
  server.on("/ultrasonico", HTTP_GET, mandarPorcentaje);

  miServo.attach(PIN_SERVO); // Connect the servo
  miServo.write(90); // Initial servo position
  server.on("/alimentar", HTTP_GET, moverServo);
  
  unsigned long currentMillis = millis();

  // Leer temperatura cada 'tempInterval' milisegundos
  if (currentMillis - lastTempMillis >= tempInterval) {
    mandarTemp();
    lastTempMillis = currentMillis;
  }
  sensors.begin();
  server.on("/temperatura", HTTP_GET, mandarTemp);

  server.begin();
}

void loop() {
  server.handleClient();
  
}

void conectarWifi() {
  WiFi.begin(ssid, password);
  Serial.println("");
  Serial.print("Connecting");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}

void moverServo() {
  if (!servoEnMovimiento) {
    miServo.write(0); // Rotate the servo to a specific position (0 degrees)
    server.send(200, "text/plain", "Alimentando..."); // Send a response
    delay(10000); // Timer to return the servo after 10 seconds
    miServo.write(90); // Return to the initial position (90 degrees)
    servoEnMovimiento = true;
    delay(1000); // Delay to prevent multiple requests
    servoEnMovimiento = false;
  } else {
    server.send(400, "text/plain", "Ya se está en movimiento");
  }
}

void mandarPorcentaje() {
  long duration, distance;
  digitalWrite(PIN_TRIG, LOW);
  delayMicroseconds(2);
  digitalWrite(PIN_TRIG, HIGH);
  delayMicroseconds(10);
  digitalWrite(PIN_TRIG, LOW);
  duration = pulseIn(PIN_ECHO, HIGH);
  distance = (duration / 2) / 29.1;

  // Calcular el porcentaje
  float distanciaMaxima = 25.5;
  float distanciaMinima = 3.0;
  porcentaje = map(distance, distanciaMinima, distanciaMaxima, 100, 0);
  porcentaje = constrain(porcentaje, 0, 100);
  server.send(200, "text/plain", String(porcentaje));
}

void mandarTemp() {
  sensors.requestTemperatures();
  float temperature = sensors.getTempCByIndex(0);
  server.send(200, "text/plain", String(temperature));
}




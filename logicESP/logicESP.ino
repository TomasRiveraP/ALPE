#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <Servo.h>


// WiFi CREDENTIALS
const char *ssid = "Bibiana_2G";
const char *password = "Bi7biana";

#define PIN_TRIG D3
#define PIN_ECHO D2

float tiempo;
float distancia;
float porcentaje;

ESP8266WebServer server(80);
Servo miServo;
int servoPin = D1;


bool servoEnMovimiento = false;

void setup() {
  conectarWifi();
  
  pinMode(PIN_TRIG, OUTPUT);
  pinMode(PIN_ECHO, INPUT);
  server.on("/ultrasonico", HTTP_GET, handleUltrasonico);
  server.on("/alimentar", HTTP_GET, moverServo);
  server.begin();
}


void loop() {
  server.handleClient();
  
  
}
void conectarWifi(){
  Serial.begin(115200);
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
  miServo.attach(servoPin); // Conecta el servo al pin
  miServo.write(90); // Posición inicial del servo
  if (!servoEnMovimiento) {
    miServo.write(0); // Gira el servo a una posición específica (0 grados)
    server.send(200, "text/plain", "Alimentando..."); // Enviar una respuesta
    delay(10000); // Temporizador para regresar el servo después de 10 segundos
    miServo.write(90); // Vuelve a la posición inicial (90 grados)
    servoEnMovimiento = true;
    delay(1000); // Temporizador para regresar el servo después de 10 segundos
    servoEnMovimiento = false;
  } else {
    server.send(400, "text/plain", "Ya se está en movimiento");
  }
}
void handleUltrasonico() {
  digitalWrite(PIN_TRIG, LOW);
  delayMicroseconds(4);
  digitalWrite(PIN_TRIG, HIGH);
  delayMicroseconds(10);
  digitalWrite(PIN_TRIG, LOW);
  tiempo = pulseIn(PIN_ECHO, HIGH);
  distancia = tiempo / 58.3;

  float distanciaMaxima = 25.5;
  float distanciaMinima = 3.0;  
  porcentaje = ((distanciaMaxima - distancia) / (distanciaMaxima - distanciaMinima)) * 100;
  if (porcentaje < 0) {
    porcentaje = 0;
  } else if (porcentaje > 100) {
    porcentaje = 100;
  }
  
  server.send(200, "text/plain", String(porcentaje, 1));
}




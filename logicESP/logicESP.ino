#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <Servo.h>


// WiFi CREDENTIALS
const char *ssid = "Bibiana_2G";
const char *password = "Bi7biana";



ESP8266WebServer server(443);
Servo miServo;
int servoPin = D1;

bool servoEnMovimiento = false;

void setup() {
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

  miServo.attach(servoPin); // Conecta el servo al pin
  miServo.write(90); // Posición inicial del servo

  
  server.on("/accion", HTTP_GET, [](){
    if (!servoEnMovimiento) {
      miServo.write(0); // Gira el servo a una posición específica (0 grados, por ejemplo)
      server.send(200, "text/plain", "Alimentando..."); // Enviar una respuesta
      servoEnMovimiento = true;
      delay(10000); // Temporizador para regresar el servo después de 10 segundos
      miServo.write(90); // Vuelve a la posición inicial (90 grados)
      servoEnMovimiento = false;
    } else {
      server.send(400, "text/plain", "Ya se está en movimiento");
    }
  });
  server.begin();
}


void loop() {
  server.handleClient();
}

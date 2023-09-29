const express = require('express');
const request = require('request');

const app = express();
const port = 3000;


app.get('/accion', (req, res) => {
  res.send('Hola, este es el servidor intermedio.');
  const esp8266Url = 'http://192.168.5.110/accion';
  request(esp8266Url, (error, response, body) => {
    if (!error && response.statusCode === 200) {

      res.send(body);
    } else {

      res.status(500).send('Error al comunicarse con el ESP8266');
    }
  });
});

app.listen(port, () => {
  console.log(`Servidor intermedio en ejecución en el puerto ${port}`);

});
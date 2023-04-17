int butIzquierda=2;
int butArriba=3;
int butAbajo=4;
int butDerecha=5;
int butClick=6;
int butLed=7;
int led=8;

int bocina=10;
  
void setup()
{
  Serial.begin(9600);
  pinMode(butIzquierda, INPUT);
  pinMode(butArriba, INPUT);
  pinMode(butAbajo, INPUT);
  pinMode(butDerecha, INPUT);
  pinMode(butClick, INPUT);
  pinMode(butLed, INPUT);
  pinMode(led,OUTPUT);
  pinMode(bocina, OUTPUT);
}

void loop()
{
  if (Serial.available() > 0) {
  int bytesRecibidos = Serial.available();
  while (bytesRecibidos > 0) {
    byte dato = Serial.read();
    bytesRecibidos--;
    if (dato == 83) { // Si el byte leído es igual a 83
      digitalWrite(led, HIGH);
      delay(1000);
      digitalWrite(led, LOW);
      tone(bocina, 1000);
      delay(1000);
      noTone(bocina);
    } else if (dato == 80) { // Si el byte leído es igual a 80
      tone(bocina, 1500);
      delay(1000);
      noTone(bocina);
      delay(500);
      tone(bocina, 1500);
      delay(1000);
      noTone(bocina);
      delay(500);
      tone(bocina, 1500);
      delay(1000);
      noTone(bocina);
      delay(500);
    }
  }
}
  int bIzq=digitalRead(butIzquierda);
  int bArr=digitalRead(butArriba);
  int bAba=digitalRead(butAbajo);
  int bDer=digitalRead(butDerecha);
  int bClick=digitalRead(butClick);
  int bLed=digitalRead(butLed);
  
  if(bIzq==HIGH){
    delay(100);
    Serial.println("se oprimio boton izquierda");
    delay(100);
    
  }
  if(bArr==HIGH){
    delay(100);
    Serial.println("se oprimio boton arriba");
    delay(100);
    
  }
  if(bAba==HIGH){
    delay(100);
    Serial.println("se oprimio boton abajo");
    delay(100);
    
  }
  if(bDer==HIGH){
    delay(100);
    Serial.println("se oprimio boton derecha");
    delay(100);
    
  }
  if(bLed==HIGH){
    delay(200);
    Serial.println("se oprimio boton led");
    digitalWrite(led,HIGH);
    delay(1000);
    digitalWrite(led,LOW);
  }
  if(bClick==HIGH){
    tone(bocina,1000,1000);
  }
  
}

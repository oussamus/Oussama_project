int LED = 13;
int LED_VERIFICATION = 12;


int data;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.println(">> START<<");  
  pinMode(LED,OUTPUT);
}

void loop() {
  while(Serial.available())
  {
    
    char data;
    data = Serial.read();
    Serial.print(data); 
    if(data =='N'){
      digitalWrite(LED,HIGH);
      }
      else if(data =='F'){
      digitalWrite(LED,LOW);
      }
      if (LED_VERIFICATION == HIGH){
        Serial.write('N');
        Serial.print('N');
        }
        else if (LED_VERIFICATION == LOW){
        Serial.write('F');
        Serial.print('F');
        }
  
  
   /* switch (data)
    {
      
      case '1': //FORWARD
          digitalWrite(motorControllA, HIGH);
          digitalWrite(motorControllB, LOW);
          analogWrite(motorSpeed1, 255);

          digitalWrite(motorControllC, HIGH);
          digitalWrite(motorControllD, LOW);
          analogWrite(motorSpeed2, 255);

          digitalWrite(motorControllE, HIGH);
          digitalWrite(motorControllF, LOW);
          analogWrite(motorSpeed3, 255);

          digitalWrite(motorControllG, HIGH);
          digitalWrite(motorControllH, LOW);
          analogWrite(motorSpeed4, 255);
        break;
        
      case '2': //REVERSE
          digitalWrite(motorControllA, LOW);
          digitalWrite(motorControllB, HIGH);
          analogWrite(motorSpeed1, 255);

          digitalWrite(motorControllC, LOW);
          digitalWrite(motorControllD, HIGH);
          analogWrite(motorSpeed2, 255);

          digitalWrite(motorControllE, LOW);
          digitalWrite(motorControllF, HIGH);
          analogWrite(motorSpeed3, 255);

          digitalWrite(motorControllG, LOW);
          digitalWrite(motorControllH, HIGH);
          analogWrite(motorSpeed4, 255);
        break;
        
      case '3': //FORWARD LEFT
          digitalWrite(motorControllA, HIGH);
          digitalWrite(motorControllB, LOW);
          analogWrite(motorSpeed1, 255);

          digitalWrite(motorControllC, HIGH);
          digitalWrite(motorControllD, LOW);
          analogWrite(motorSpeed2, 255);

          digitalWrite(motorControllE, LOW);
          digitalWrite(motorControllF, HIGH);
          analogWrite(motorSpeed3, 225);

          digitalWrite(motorControllG, LOW);
          digitalWrite(motorControllH, HIGH);
          analogWrite(motorSpeed4, 225);
        break;
        
      case '4': //FORWARD RIGHT
          digitalWrite(motorControllA, LOW);
          digitalWrite(motorControllB, HIGH);
          analogWrite(motorSpeed1, 225);

          digitalWrite(motorControllC, LOW);
          digitalWrite(motorControllD, HIGH);
          analogWrite(motorSpeed2, 225);

          digitalWrite(motorControllE, HIGH);
          digitalWrite(motorControllF, LOW);
          analogWrite(motorSpeed3, 225);

          digitalWrite(motorControllG, HIGH);
          digitalWrite(motorControllH, LOW);
          analogWrite(motorSpeed4, 225);
        break;
               
      default: //If bluetooth module receives any value not listed above, both motors turn off
          digitalWrite(motorControllA, LOW);
          digitalWrite(motorControllB, LOW);
          analogWrite(motorSpeed1, 0);

          digitalWrite(motorControllC, LOW);
          digitalWrite(motorControllD, LOW);
          analogWrite(motorSpeed2, 0);

          digitalWrite(motorControllE, LOW);
          digitalWrite(motorControllF, LOW);
          analogWrite(motorSpeed3, 0);

          digitalWrite(motorControllG, LOW);
          digitalWrite(motorControllH, LOW);
          analogWrite(motorSpeed4, 0);
    }*/
  }
}

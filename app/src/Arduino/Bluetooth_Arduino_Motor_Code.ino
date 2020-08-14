int LED = 13;
int LED_VERIFICATION = 12;


int data;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.println(">> START<<");  
  pinMode(LED,OUTPUT);
  pinMode(LED_VERIFICATION,INPUT);
}

void loop() {
  int reading=0;
  reading = digitalRead(LED_VERIFICATION );
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
     }// while
     Serial.println(reading);
     delay(200);

}
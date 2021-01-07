
#include <SoftwareSerial.h>
SoftwareSerial Blue(0,1);


char data = '0';
int LED = 13; // Led connected
char M1_OPEN = 'a';
char M1_CLOSE = 'b';
char M2_UP = 'c';
char M2_DOWN = 'd';
char M3_UP = 'e';
char M3_DOWN = 'f';
char M4_UP = 'g';
char M4_DOWN = 'h';
char M5_LEFT = 'i';
char M5_RIGHT = 'j';


void setup()
{ 
  pinMode(LED, OUTPUT); 
  digitalWrite(LED, LOW);
  Serial.begin(9600);
  Serial.println("START");
}

void loop()
{
  
   if(Serial.available()>0) 
      {
        data = Serial.read();
      }
      
      if (data == 'N')
      {
        digitalWrite(LED,HIGH);
        Serial.println("LED ON "); 
       }
       
       if( data == 'F')
       {
        digitalWrite(LED,LOW);
        Serial.println("LED OFF");
       }
       if (data == M1_OPEN){
          Serial.println("Motor 1 open");
          }
       else if (data == M1_CLOSE){
          Serial.println("Motor 1 close");
          } 
       else if (data == M2_UP){
          Serial.println("Motor 2 up");
          }
       else if (data == M2_DOWN){
          Serial.println("Motor 2 down");
          }   
       else if (data == M3_UP){
          Serial.println("Motor 3 up");
          }
       else if (data == M3_DOWN){
          Serial.println("Motor 3 down");
          } 
       else if (data == M4_UP){
          Serial.println("Motor 4 up");
          }
       else if (data == M4_DOWN){
          Serial.println("Motor 4 down");
          } 
       else if (data == M5_LEFT){
        Serial.println("Motor 5 left");
        }
        else if (data == 'j'){
        Serial.println("Motor 5 right");
        }
      else Serial.println(data);
      //data = '0';
      delay(500);
 }


#include <SoftwareSerial.h>
SoftwareSerial Blue(0,1);


char data = '0';
// Declaring the pins used 
int LED = 13;
int M1O = 12;
int M1C = 11;
int M2U = 2;
int M2D = 3;
int M3U = 4;
int M3D = 5;
int M4U = 6;
int M4D = 7;
int M5L = 8;
int M5R = 9;
// The commands from the Android App
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
  pinMode(M1O, OUTPUT); 
  pinMode(M1C, OUTPUT); 
  pinMode(M2U, OUTPUT); 
  pinMode(M2D, OUTPUT); 
  pinMode(M3U, OUTPUT); 
  pinMode(M3D, OUTPUT); 
  pinMode(M4U, OUTPUT); 
  pinMode(M4D, OUTPUT); 
  pinMode(M5L, OUTPUT); 
  pinMode(M5R, OUTPUT); 
  ResetMotors();
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
          digitalWrite(M1O, HIGH);
          digitalWrite(M1C, LOW);
          }
       else if (data == M1_CLOSE){
          Serial.println("Motor 1 close");
          digitalWrite(M1O, LOW);
          digitalWrite(M1C, HIGH);
          } 
       else if (data == M2_UP){
          Serial.println("Motor 2 up");
          digitalWrite(M2U, HIGH);
          digitalWrite(M2D, LOW);
          }
       else if (data == M2_DOWN){
          Serial.println("Motor 2 down");
          digitalWrite(M2U, LOW);
          digitalWrite(M2D, HIGH);
          }   
       else if (data == M3_UP){
          Serial.println("Motor 3 up");
          digitalWrite(M3U, HIGH);
          }
       else if (data == M3_DOWN){
          Serial.println("Motor 3 down");
          digitalWrite(M3D, HIGH);
          } 
       else if (data == M4_UP){
          Serial.println("Motor 4 up");
          digitalWrite(M4U, HIGH);
          }
       else if (data == M4_DOWN){
          Serial.println("Motor 4 down");
          digitalWrite(M4D, HIGH);
          } 
       else if (data == M5_LEFT){
        Serial.println("Motor 5 left");
        digitalWrite(M5L, HIGH);
        }
        else if (data == 'j'){
        Serial.println("Motor 5 right");
        digitalWrite(M5R, HIGH);
        }
      else { 
           ResetMotors();
          }
 }
void ResetMotors(){
  digitalWrite(LED, LOW);
  digitalWrite(M1O, LOW);
  digitalWrite(M1C, LOW);
  digitalWrite(M2U, LOW);
  digitalWrite(M2D, LOW);
  digitalWrite(M3U, LOW);
  digitalWrite(M3D, LOW);
  digitalWrite(M4U, LOW);
  digitalWrite(M4D, LOW);
  digitalWrite(M5L, LOW);
  digitalWrite(M5R, LOW);
}

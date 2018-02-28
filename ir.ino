//------------------------------------------------------------------------------
// Include the IRremote library header
//
#include <IRremote.h>

#define BOTAO_0 2048
#define BOTAO_1 2049
#define BOTAO_2 2050
#define BOTAO_3 2051
#define BOTAO_4 2052
#define BOTAO_5 2053
#define BOTAO_6 2054
#define BOTAO_7 2055
#define BOTAO_8 2056
#define BOTAO_9 2057
#define BOTAO_0ALT 0
#define BOTAO_1ALT 1
#define BOTAO_2ALT 2
#define BOTAO_3ALT 3
#define BOTAO_4ALT 4
#define BOTAO_5ALT 5
#define BOTAO_6ALT 6
#define BOTAO_7ALT 7
#define BOTAO_8ALT 8
#define BOTAO_9ALT 9

bool ligado[9] = {false, false, false, false, false, false, false, false, false};

//------------------------------------------------------------------------------
// Tell IRremote which Arduino pin is connected to the IR Receiver (TSOP4838)
//
int recvPin = 11;
IRrecv irrecv(recvPin);

//+=============================================================================
// Configure the Arduino
//
void  setup ( )
{
  Serial.begin(9600);   // Status message will be sent to PC at 9600 baud
  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  irrecv.enableIRIn();  // Start the receiver
  for(int i = 2; i < 11; i++){
    digitalWrite(i, HIGH);
    delay(50);
    digitalWrite(i, LOW);
  }
}

char stats[] = {'0', '0', '0', '0', '0', '0', '0', '0', '0'};
void  loop ( )
{
  Serial.println(stats);
  decode_results  results;        // Somewhere to store the results
  delay(100);
  
  if (irrecv.decode(&results)) {  // Grab an IR code
    if(results.value == BOTAO_0 || results.value == BOTAO_0ALT){
      ligado[0] = false;
      stats[0] = '0';
      ligado[1] = false;
      stats[1] = '0';
      ligado[2] = false;
      stats[2] = '0';
      ligado[3] = false;
      stats[3] = '0';
      ligado[4] = false;
      stats[4] = '0';
      ligado[5] = false;
      stats[5] = '0';
      ligado[6] = false;
      stats[6] = '0';
      ligado[7] = false;
      stats[7] = '0';
      ligado[8] = false;
      stats[8] = '0';
      digitalWrite(0, LOW);
      digitalWrite(1, LOW);
      digitalWrite(2, LOW);
      digitalWrite(3, LOW);
      digitalWrite(4, LOW);
      digitalWrite(5, LOW);
      digitalWrite(6, LOW);
      digitalWrite(7, LOW);
      digitalWrite(8, LOW);
    }
    if(results.value == BOTAO_1 || results.value == BOTAO_1ALT){
      if(ligado[0]){
        digitalWrite(2, LOW);
        ligado[0] = false;
        stats[0] = '0';
      }
       else{
        digitalWrite(2, HIGH);
        ligado[0] = true;
        stats[0] = '1';
       }  
    }
    if(results.value == BOTAO_2 || results.value == BOTAO_2ALT){
      if(ligado[1]){
        digitalWrite(3, LOW);
        ligado[1] = false;
        stats[1] = '0';
      }
       else{
        digitalWrite(3, HIGH);
        ligado[1] = true;
        stats[1] = '1';
       }  
    }
    if(results.value == BOTAO_3 || results.value == BOTAO_3ALT){
      if(ligado[2]){
        digitalWrite(4, LOW);
        ligado[2] = false;
        stats[2] = '0';
      }
       else{
        digitalWrite(4, HIGH);
        ligado[2] = true;
        stats[2] = '1';
       }  
    }
    if(results.value == BOTAO_4 || results.value == BOTAO_4ALT){
      if(ligado[3]){
        digitalWrite(5, LOW);
        ligado[3] = false;
        stats[3] = '0';
      }
       else{
        digitalWrite(5, HIGH);
        ligado[3] = true;
        stats[3] = '1';
       }  
    }
    if(results.value == BOTAO_5 || results.value == BOTAO_5ALT){
      if(ligado[4]){
        digitalWrite(6, LOW);
        ligado[4] = false;
        stats[4] = '0';
      }
       else{
        digitalWrite(6, HIGH);
        ligado[4] = true;
        stats[4] = '1';
       }  
    }
    if(results.value == BOTAO_6 || results.value == BOTAO_6ALT){
      if(ligado[5]){
        digitalWrite(7, LOW);
        ligado[5] = false;
        stats[5] = '0';
      }
       else{
        digitalWrite(7, HIGH);
        ligado[5] = true;
        stats[5] = '1';
       }  
    }
    if(results.value == BOTAO_7 || results.value == BOTAO_7ALT){
     if(ligado[6]){
        digitalWrite(8, LOW);
        ligado[6] = false;
        stats[6] = '0';
      }
       else{
        digitalWrite(8, HIGH);
        ligado[6] = true;
        stats[6] = '1';
       }  
    }
    if(results.value == BOTAO_8 || results.value == BOTAO_8ALT){
     if(ligado[7]){
        digitalWrite(9, LOW);
        ligado[7] = false;
        stats[7] = '0';
      }
       else{
        digitalWrite(9, HIGH);
        ligado[7] = true;
        stats[7] = '1';
       }  
    }
    if(results.value == BOTAO_9 || results.value == BOTAO_9ALT){
     if(ligado[8]){
        digitalWrite(10, LOW);
        ligado[8] = false;
        stats[8] = '0';
      }
       else{
        digitalWrite(10, HIGH);
        ligado[8] = true;
        stats[8] = '1';
       }  
    }
    delay(50);
    irrecv.resume();              // Prepare for the next value
  }
}

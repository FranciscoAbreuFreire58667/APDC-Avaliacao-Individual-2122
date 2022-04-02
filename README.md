# APDC-Avaliacao-Individual-2122

## Francisco Abreu Freire 58667

### Caros Professores,

Gostaria de começar por dizer, que por motivos de tempo, não me foi possível realizar a parte da interface do user. Optei por dar prioridade às operações. Peço          imensa desculpa, porque sei que vai dificultar a avaliação. Para testar as minhas operações usei o Postman. Abaixo estão as indicações de como testar as                operações. Mais uma vez, peço desculpa.
        
        
        
### Testar as operações:



Operação 1:

As variáveis obrigatorias de input são username, email, name , password e passwordConfirmation. As outras, como pedido no enunciado, são opcionais (Ficando UNDEFINED caso nao sejam agora introduzidas).

Um exemplo desta operação em JSON :
        
 #### URL a usar: https://arched-glass-344017.appspot.com/rest/test/op1
 
{
    "username" : "User1",
    "email" : "teste@gmail.com",
    "name" : "nome1",
    "password" : "1234567889",
  "passwordConfirmation" : "1234567889",
 "landlineNumber":"landlineNumber",
 "mobilePhoneNumber":"mobilePhoneNumber"
}


-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

        
 Operação 2:

As variáveis obrigatorias de input são username a remover. O user que remove tem como username o valor que está no path.

Um exemplo desta operação em JSON :
     
 #### URL a usar: https://arched-glass-344017.appspot.com/rest/test/op6/ AQUI FICA UM USERNAME /op2
 
{
    "username" : "User1"
}       
        

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

 Operação 3:

O user que pede para imprimir os users tem como username o valor que está no path.

Um exemplo desta operação em JSON :
        
 #### URL a usar: https://arched-glass-344017.appspot.com/rest/test/op6/ AQUI FICA UM USERNAME /op3
 

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------


 Operação 4:

O user que pede para alterar os atributos tem como username o valor que está no path. O input necessita obrigatoriamente de um username vaçida para este ter o seus atributos alterados.

Um exemplo desta operação em JSON :
        
 #### URL a usar: https://arched-glass-344017.appspot.com/rest/test/op6/ AQUI FICA UM USERNAME /op3
 
{
"username" : "User1",
"NIF" : "1234566789",
"profilePublic" : "true"
}


-----------------------------------------------------------------------------------------------------------------------------------------------------------------------


Operação 5:

O user que pede para alterar a sua palavra passe tem como username o valor que está no path. O input necessita obrigatoriamente de ter a password antiga e a nova e a sua confirmação. 

Um exemplo desta operação em JSON :
        
 #### URL a usar: https://arched-glass-344017.appspot.com/rest/test/op6/ AQUI FICA UM USERNAME /op5

{
	"oldPassword" : "1234567889",
	"newPassword" : "1",
	"confirmation" : "1"

}


-----------------------------------------------------------------------------------------------------------------------------------------------------------------------


Operação 6:

Input tem obrigatoriamente username e password atual. 

Um exemplo desta operação em JSON :
        
 #### URL a usar: https://arched-glass-344017.appspot.com/rest/test/op6

{
    "username" : "User1",
    "password" : "1234567889"
}



-----------------------------------------------------------------------------------------------------------------------------------------------------------------------



Operação 7:

O user que pede o token tem como username o valor que está no path.

Um exemplo desta operação em JSON :
        
 #### URL a usar:  #### URL a usar: https://arched-glass-344017.appspot.com/rest/test/op6/ AQUI FICA UM USERNAME /op7



-----------------------------------------------------------------------------------------------------------------------------------------------------------------------



Operação 8:

O user que pede o log out tem como username o valor que está no path.

Um exemplo desta operação em JSON :
        
 #### URL a usar:  #### URL a usar: https://arched-glass-344017.appspot.com/rest/test/op6/ AQUI FICA UM USERNAME /op8


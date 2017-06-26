Feature: Being able to login
Scenario: Login
  Login with some username

    Given a list of users:
      | username    | password |
      | pepe@uniovi.es    | 01234   |
      | katia@uniovi.es    | 01234     |
      | pruebausuario@uniovi.es    | 01234      |
    When I login with name "pepe@uniovi.es" and password "01234"
    Then I receive a welcome message
    
    
  
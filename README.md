# TodoApp_Backend
A Maven project with APIs developed using Jersey for RESTful Web Services for To-do web application. It uses MySQL as database. It is deployed at Heroku and MySQL is hosted at RemoteMysql.com

## Steps to make changes and deploy -

1. Make changes (Eclipse Neon or any IDE)
2. Right Click project in Eclipse and select 'Run As' --> 'Maven Build'(2nd one)
3. In goal write - clean install
4. see console and it should display 'BUILD SUCCESS'
5. git add .
6. git commit -m "msg"
7. git push origin master

==> Your updated code will be deployed on heroku.

Test by accessing URL - https://todoapp-backend-jersey.herokuapp.com/api/hello

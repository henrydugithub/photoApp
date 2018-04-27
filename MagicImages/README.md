This is a Spring boot application with photos display application

The application should list all photos loaded from third party service once the app's landing page launched
It will consumes the rest end point: https://5ad8d1c9dc1baa0014c60c51.mockapi.io/api/br/v1/magic/{id}

It will expose following rest api endpoint for consume from web api or browser
localhost:8080/api/v1/photos		which will return all photos loaded with json fortmat
localhost:8080/api/v1/photos/{id} 	which will return the specific photo with the photo id loaded with json fortmat
localhost:8080/api/v1/photos/pagnation?offset=1&limit=2


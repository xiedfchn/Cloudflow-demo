# cloudflow-demo
Cloudflow is an SDK and a set of Kubernetes extensions that accelerates your development of distributed streaming applications and facilitates their deployment on Kubernetes
## Run the app
- Sending sample data to this port will exercise the pipeline: \
`$ curl -i -X POST localhost:3000 -H "Content-Type: application/json" --data '{"deviceId":"c75cb448-df0e-4692-8e06-0321b7703992","timestamp":1495545646279,"measurements":{"power":1.7,"rotorSpeed":3.9,"windSpeed":105.9}}'`
- Trying to send invalid data such as:\
`$ curl -i -X POST localhost:3000 -H "Content-Type: application/json" --data '{"deviceId":"c75cb448-df0e-4692-8e06-0321b7703992","timestamp":1495545646279,"measurements":{"power":1.7,"rotorSpeed":3.9,"windSpeed":-105.9}}'`

https://cloudflow.io/docs/current/get-started/run-in-sandbox.html

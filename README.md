# e-rommerce-microservices

This is implementation of [monolithic e-rommerce](https://github.com/RobSil/e-rommerce) but in microservice architecture.


Microservices:
 - cartservice (to separate from productservice, should not return product entity, just ids, for further fetching from product service)
 - productservice (contains category and product logic)
 - orderservice
 - notificationservice (may send emails and some system notifications)
 - userservice
 - paymentservice
 - (additional system needs services like: gateway, configservice, etc.)

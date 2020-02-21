# Kata: Train Reservation

Coltrain is a web application that provides user with a REST API to book seats of a train. The app relies on the railway operator API to :

* get a unique reservation number
* get the topology of a train (coaches, seats identifier and availability)
* post the actual reservation

Call to those APIs are billed and the cost is high. You're working on the "Coltrain" application, and your task is to understand why Coltrain does so many calls to the railway operator API and to see if we reduce them (and doing so, recucing the bill).

This kata was originally created by [Emily Bache](https://github.com/emilybache/KataTrainReservation)

## Business Rules around Reservations

There are various business rules and policies around which seats may be reserved. For a train overall, **no more than 70% of seats may be reserved** in advance, and ideally no individual coach should have **no more than 70% reserved seats either**. However, there is another business rule that says you **must put all the seats for one reservation in the same coach**. This could make you and go over 70% for some coaches, just make sure to keep to 70% for the whole train.

## Hexagonal Architecturee

The ultimate goal of this kata is revamp the project into a [hexagonal architecture](https://softwarecampament.wordpress.com/portsadapters/).

## Coltrain Service

The Coltrain service responds to a HTTP POST request that comes with form data telling you which train the customer wants to reserve seats on, and how many they want. It should return a json document detailing the reservation that has been made.

A reservation comprises a json document with three fields, the train id, booking reference, and the ids of the seats that have been reserved. Example json:

```json
{"train_id": "express_2000", "booking_reference": "75bcd15", "seats": ["1A", "1B"]}
```

If it is not possible to find suitable seats to reserve, the service should instead return an empty list of seats and an empty string for the booking reference.

### Booking Reference Service

You can get a unique booking reference using a REST-based service. For test purposes, you can start a local service using the provided code in the "booking_reference_service" folder. You can assume the real service will behave the same way, but be available on a different url.

Install [Python 3.3](http://python.org) and [CherryPy](http://www.cherrypy.org/), then start the server by running:

    python booking_reference_service.py

You can use this service to get a unique booking reference. Make a GET request to:

    http://localhost:8082/booking_reference

This will return a string that looks a bit like this:

    75bcd15

### Train Data Service

You can get information about which each train has by using the train data service. For test purposes, you can start a local service using the provided code in the "train_data_service" folder. You can assume the real service will behave the same way, but be available on a different url.

Again, you need [Python 3.3](http://python.org) and [CherryPy](http://www.cherrypy.org/), then start the server by running:

    python start_service.py

You can use this service to get data for example about the train with id "express_2000" like this:

    http://localhost:8081/data_for_train/express_2000

this will return a json document with information about the seats that this train has. The document you get back will look for example like this:

    {"seats": {"1A": {"booking_reference": "", "seat_number": "1", "coach": "A"}, "2A": {"booking_reference": "", "seat_number": "2", "coach": "A"}}}

Note I've left out all the extraneous details about where the train is going to and from, at what time, whether there's a buffet car etc. All that's there is which seats the train has, and if they are already booked. A seat is available if the "booking_reference" field contains an empty string. To reserve seats on a train, you'll need to make a POST request to this url:

    http://localhost:8081/reserve

and attach form data for which seats to reserve. There should be three fields:

    "train_id", "seats", "booking_reference"

The "seats" field should be a json encoded list of seat ids, for example:

    '["1A", "2A"]'

The other two fields are ordinary strings. Note the server will prevent you from booking a seat that is already reserved with another booking reference.

The service has one additional method, that will remove all reservations on a particular train. Use it with care:

    http://localhost:8081/reset/express_2000

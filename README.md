# TechTest

## Assumptions
UI is a basic but meets the basic specified requirement.

No currency is assumed if is just displayed as a rounded down integer.

## Structure
This project follows clean architecture. The network data structures are separated from the domain model.

I used Koin as the DI library. It's easier to get up and running with Koin than Dagger2

The core uses Retrofit/OKHttp using Kotlinx Serialization for comms with the API's. The model classes used for the API's are defined in `com.sfeatherstone.propertyPriceAverage.repository.network.model`. The API's are controlled by three repositories. The repositories interfaces only use application models. Mapper functions are used to convert between the API model and the application model

Application models are defined in `com.sfeatherstone.propertyPriceAverage.model`. This includes the `State` classe that used sealed classes to represent different states that data can be in (Loading/Success/Error)

I have a single activity that has a single viewmodel.

The viewmodel invokes a usecase. The usecase return a `Either` with `right` being success and `left` being fail with an exception. The viewmodels coroutines scope is used.

The usecases act as the link between the viewmodel and the repositories. The `GetPropertyPriceAverage` does the calculation to get the average from the list of properties returned.

Unittests cover 66% of the lines in `com.sfeatherstone.propertyPriceAverage`

Instrumented tests cover the changes to the UI as the LiveData changes in the ViewModel

## Thoughts
Kotlinx Serialization is still pre 1.0 so not ready for production.

Doing this as Clean is slow. I used a previous project to avoid a lot of boiler plate.

I didn't implement a OkHttp mock server so the deserialation is not tested.

I didn't run ktlint so my formatting might be a bit off.

This took me about 5 hours to do. The most expensive part was mocking ViewModel/LiveData in the Instrumented Tests
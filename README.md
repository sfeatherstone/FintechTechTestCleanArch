# StarlingTechTest
Simon Featherstone +44 07909 920671 mailto:simon@wedgetech.co.uk

## Assumptions
UI is a very basic. Task was more about the API interaction

GBP currency is assumed. No provision was made other currencies with potential different formatting.

API is controlled by having a valid in `com.sfeatherstone.starlingroundup.ConstantsKt.API_ACCESS_TOKEN` This needs replacing when it becomes invalid.

Because `/api/v2/account/{accountUid}/savings-goals` is not idempotent, I needed to control not creating multiple savings goals through the name.

Only 'OUT' transactions are counted but not including payments made to the topup account.

The first account for access token is used. There is no way of selecting the second/third account

The topup transaction is made idempotent by using a static transaction UUID

DNS Pinning and other security was looked at.

## Structure
This project follows clean architecture. I got to speak to Dimitar Lazarov​ about using Clean in my last project and I wanted to exercise that rather than going for a quicker dirtier solution

I used Koin as the DI library. It's easier to get up and running with Koin than Dagger2

The core uses Retrofit/OKHttp using Kotlinx Serialization for comms with the API's. The model classes used for the API's are defined in `com.sfeatherstone.starlingroundup.repository.network.model`. The API's are controlled by three repositories. The repositories interfaces only use application models. Mapper functions are used to convert between the API model and the application model

Application models are defined in `com.sfeatherstone.starlingroundup.model`. This includes the `State` classes that used sealed classes to represent different states that data can be in (Loading/Success/Error)

I have a single activity that has a single viewmodel. The view model could be split, but for the sake of time I have just used one.

The viewmodel invokes usecases. The usecases return a `Either` with `right` being success and `left` being fail with an exception. The viewmodels coroutines scope is used.

The usecases act as the link between the viewmodel and the repositories. Some contain application logic.

Some unittest have been provided including testing getting the transaction feed and getting the roundup total. More time would mean more unittests.

## Flow
The single activity queries the API for `accountUid` and `defaultCategory`.

Once it has this it gets other basic account information and loads the list of savings goals. If a goal can't be found with the name `Round-up` then one is created.

After getting the savings goal UUID the tranactions for the last 7x24hrs are queried. A count and roundup total for all the "OUT" transactions that are not to the savings goal UUID are returned.

The round up total is presented on the screen.

A button can be pressed to send the transaction for the transfer to the savings goal. It uses a static transaction UUID to prevent multiple transactions per instance.

## Thoughts
Kotlinx Serialization is still pre 1.0 so not ready for production.

Doing this as Clean is slow. There is a lot of boiler plate coding required even with Kotlin. But I am pleased with the error handling in this example. Some of the application model classes feel a bit messy, but do enough to get this task done.



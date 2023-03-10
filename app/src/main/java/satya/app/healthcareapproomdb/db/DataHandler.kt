package satya.app.healthcareapproomdb.db

sealed class DataHandler<T>(val data: T? = null, val errorMessage: String? = null) {
    class LOADING<T> : DataHandler<T>()
    class SUCCESS<T>(data: T? = null) : DataHandler<T>(data = data)
    class ERROR<T>(errorMessage: String) : DataHandler<T>(errorMessage = errorMessage)
}

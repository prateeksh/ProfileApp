package company.project.profileapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import company.project.profileapp.repository.ProfileRepository
import company.project.profileapp.model.ExpandableUserModel
import company.project.profileapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//A View Model class
class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    //Dispatcher.IO is used to block the threads for IO operation (kotlin coroutines)
    fun init(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUsersData()
        }

    }

    val user: LiveData<User>
    get() = repository.users

    fun prepareDataForExpandableAdapter(user: User) : MutableList<ExpandableUserModel>{
        var expandableUserModel = mutableListOf<ExpandableUserModel>()
        for (data in user.data) {
            expandableUserModel.add(ExpandableUserModel(ExpandableUserModel.PARENT, data))
        }
        return expandableUserModel
    }
}
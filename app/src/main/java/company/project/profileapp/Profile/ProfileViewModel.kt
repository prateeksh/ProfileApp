package company.project.profileapp.Profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import company.project.profileapp.model.ExpandableUserModel
import company.project.profileapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {


    // var mUserData : MutableLiveData<User>?= null

    fun init(){
        Log.e("TAG", "in view model")
        viewModelScope.launch(Dispatchers.IO) {
           /* if (mUserData != null) {
                mUserData
            } else mUserData = MutableLiveData()
*/

            //mRepository = ProfileRepository.getRepoInstance()
            //mUserData = repository.getUsersData()
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
/*
    fun prepareDataForSectionedAdapter(user: User) : MutableList<ExpandableUserModel>{
        var expandableCountryModel = mutableListOf<ExpandableUserModel>()
        for (data in user.data) {
            expandableCountryModel.add(ExpandableUserModel(ExpandableUserModel.PARENT,data))

            for(job in data.job){
                expandableCountryModel.add(ExpandableUserModel(ExpandableUserModel.CHILD,job))
            }
            for(edu in data.education){
                expandableCountryModel.add(ExpandableUserModel(ExpandableUserModel.CHILD,edu))
            }
        }
        return expandableCountryModel
    }*/
}
package company.project.profileapp.model

class ExpandableUserModel {

    companion object{
        const val PARENT = 1
        const val CHILD = 2
    }

    lateinit var userParent : User.Data
    var type: Int
    var userChildEdu : User.Data.Education? = null
    var userChildJob: User.Data.Job? = null

    var isExpanded: Boolean
    private var isCloseShown : Boolean

    constructor(type: Int, userParent: User.Data, isExpanded : Boolean = false, isCloseShown : Boolean = false){

        this.type = type
        this.userParent = userParent
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown
    }

    constructor(type: Int, userChildEdu: User.Data.Education, isExpanded : Boolean = false, isCloseShown : Boolean = false){

        this.type = type
        this.userChildEdu = userChildEdu
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown
    }

    constructor(type: Int, userChildJob: User.Data.Job, isExpanded : Boolean = false, isCloseShown : Boolean = false){

        this.type = type
        this.userChildJob = userChildJob
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown
    }
}
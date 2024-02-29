package com.sky.officeconnectandroid

import com.sky.officeconnectandroid.models.User
import com.sky.officeconnectandroid.repository.AuthRepository
import com.sky.officeconnectandroid.repository.UserRepository
import com.sky.officeconnectandroid.viewmodel.MyProfileViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MyProfileViewModelTest {
    private lateinit var viewModel: MyProfileViewModel

    @Before
    fun setup() {
        val authRepository = AuthRepository()
        val userRepository = UserRepository()
        viewModel = MyProfileViewModel(authRepository, userRepository)
    }

    @Test
    fun `setUserEventListener should update user state`() {
        viewModel.setUserEventListener()
        // Assert that the user state has been updated
        assertNotNull(viewModel.myProfileUIState.user)
    }

    @Test
    fun `onNameChange should update editableName in user state`() {
        val name = "John Doe"
        viewModel.onNameChange(name)
        // Assert that editableName has been updated
        assertEquals(name, viewModel.myProfileUIState.editableName)
    }

    @Test
    fun `onUpdateUser should update the user information`() {
        val user = User("John Doe", "Location", "Department", "Job Title")
        viewModel.myProfileUIState = viewModel.myProfileUIState.copy(
            editableName = user.name,
            editableLocation = user.location,
            editableDepartment = user.department,
            editableJobTitle = user.jobTitle
        )
        viewModel.onUpdateUser()
        // Assert that the user information has been updated
        assertEquals(user, viewModel.myProfileUIState.user)
    }

//    @Test
//    fun `onDeleteUser should delete the user account`() {
//        // Mocking the required context and password
//        val context: Context = mock()
//        val password = "password"
//        val onCompleteCallback: (Boolean) -> Unit = mock()
//
//        // Invoke the method
//        viewModel.onDeleteUser(context, password, onCompleteCallback)
//
//        // Verify the expected behavior, e.g., using Mockito verify() method
//        verify(viewModel.userRepository).deleteUser(viewModel.userID, viewModel.myProfileUIState.user)
//        verify(context).showToast("Account data deleted")
//        verify(authRepository).deleteUser(any())
//        verify(onCompleteCallback).invoke(true)
//    }
}
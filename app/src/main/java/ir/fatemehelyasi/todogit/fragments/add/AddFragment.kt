package ir.fatemehelyasi.todogit.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import ir.fatemehelyasi.todogit.R
import ir.fatemehelyasi.todogit.data.models.ToDoData
import ir.fatemehelyasi.todogit.data.viewmodel.ToDoViewModel
import ir.fatemehelyasi.todogit.databinding.FragmentAddBinding
import ir.fatemehelyasi.todogit.fragments.SharedViewModel


class AddFragment : Fragment() {
   private lateinit var binding: FragmentAddBinding
    //--------06
    private val mToDoViewModel:ToDoViewModel by viewModels()
    private val mSharedViewModel:SharedViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(layoutInflater)

        binding.spinner.onItemSelectedListener=mSharedViewModel.listener

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //----------------------------------------------------------------------------menu
        // The usage of an interface lets you inject your own implementation
        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.add_fragment_menu, menu)
            }

            //---------0.0
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                if (menuItem.itemId == R.id.menu_add) {
                    insertDataToDb()

                }

                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    //--------------------------------01
    //fill data
    private fun insertDataToDb() {
        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.spinner.selectedItem.toString()
        val mDescription = binding.descriptionEt.text.toString()
    //----------------03
        val validation =mSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            //insert Data to Database
            val newData = ToDoData(
                0,
                mTitle,
                //------5
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            //--07
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(),"Successfully Added ! ",Toast.LENGTH_SHORT).show()
            //--08
            //navigation back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out All field ! ",Toast.LENGTH_SHORT).show()

        }
    }

}

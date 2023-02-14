package ir.fatemehelyasi.todogit.fragments.update

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cn.pedant.SweetAlert.SweetAlertDialog
import ir.fatemehelyasi.todogit.R
import ir.fatemehelyasi.todogit.data.models.Priority
import ir.fatemehelyasi.todogit.data.models.ToDoData
import ir.fatemehelyasi.todogit.data.viewmodel.ToDoViewModel
import ir.fatemehelyasi.todogit.databinding.FragmentListBinding
import ir.fatemehelyasi.todogit.databinding.FragmentUpdateBinding
import ir.fatemehelyasi.todogit.fragments.SharedViewModel


class UpdateFragment : Fragment() {
    lateinit var binding: FragmentUpdateBinding


    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_update, container, false)
        binding = FragmentUpdateBinding.inflate(layoutInflater)

        //safe args receive data from list fragment
        binding.currentTitleEt.setText(args.currentItem.title)
        binding.currentDescriptionEt.setText(args.currentItem.description)
        binding.currentSpinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority)) //priority LOW
        binding.currentSpinner.onItemSelectedListener = mSharedViewModel.listener

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
                menuInflater.inflate(R.menu.update_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                when (menuItem.itemId) {
                    R.id.menu_save -> updateItem()
                    R.id.menu_delete -> confirmItemRemoval()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }

    //----------------------------------------------------------------------------
    private fun updateItem() {
        val titleUpdate = binding.currentTitleEt.text.toString()
        val descriptionUpdate = binding.currentDescriptionEt.text.toString()
        val getPriorityUpdate = binding.currentSpinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(titleUpdate, descriptionUpdate)
        if (validation == true) {
            val updateItem = ToDoData(
                args.currentItem.id,
                titleUpdate,
                mSharedViewModel.parsePriority(getPriorityUpdate),
                descriptionUpdate
            )
            mToDoViewModel.updateData(updateItem)
            Toast.makeText(requireContext(), "Successfully Update ", Toast.LENGTH_SHORT).show()
            //Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        } else {
            Toast.makeText(requireContext(), "Pleas Fill out all fields  ", Toast.LENGTH_SHORT)
                .show()

        }

    }

    //----------------------------------------------------------------------------
    private fun confirmItemRemoval() {

        val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)

        dialog.titleText = "Are you sure you want to remove ${args.currentItem.title}?"
        dialog.confirmText="Delete"
        dialog.cancelText="Cancel"

        dialog.setCancelClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            Toast.makeText(requireContext(), "Canceled to remove: ${args.currentItem.title} ", Toast.LENGTH_SHORT).show() }

        dialog.setConfirmClickListener {
            mToDoViewModel.deleteData(args.currentItem)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            Toast.makeText(requireContext(), "Removed: ${args.currentItem.title} ", Toast.LENGTH_SHORT).show()
            dialog.dismiss() }

        dialog.show()
    }
    //---------------------way2

//        val dialog = AlertDialog.Builder(requireContext())
//        dialog.setPositiveButton("Yes") { _, _ ->
//            mToDoViewModel.deleteData(args.currentItem)
//            Toast.makeText(
//                requireContext(),
//                "Removed: ${args.currentItem.title} ",
//                Toast.LENGTH_SHORT
//            ).show()
//
//            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
//
//            dialog.setNegativeButton("No") { _, _ -> }
//            dialog.setTitle("Delete ${args.currentItem.title}?")
//            dialog.setMessage("Are You Sure you want to remove ${args.currentItem.title}")
//            dialog.create().show()


//----------------------------------------------------------------------------
}

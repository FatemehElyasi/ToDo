package ir.fatemehelyasi.todogit.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import ir.fatemehelyasi.todogit.R
import ir.fatemehelyasi.todogit.data.viewmodel.ToDoViewModel
import ir.fatemehelyasi.todogit.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val Adapter: MyListAdapter by lazy { MyListAdapter() }
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater)

        //navigate between fragments
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }


        //recycler
        binding.recyclerview.adapter = Adapter
        binding.recyclerview.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        //viewModel
        // return list of data from dataclass database
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            //fun recycler
            Adapter.setData(it)
        })

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
                menuInflater.inflate(R.menu.list_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                if(menuItem.itemId==R.id.menu_delete_all){
                    confirmRemovalToAllData()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }
    //----------------------------------------------------------------------------//sweet Alert Dialog
    private fun confirmRemovalToAllData() {
        val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)

        dialog.titleText = "Are you sure you want to remove everything?"
        dialog.confirmText="Delete"
        dialog.cancelText="Cancel"

        dialog.setCancelClickListener {
            dialog.dismiss()
            Toast.makeText(requireContext(), "Canceled to removed everything ", Toast.LENGTH_SHORT).show() }

        dialog.setConfirmClickListener {
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(), "Removed everything ", Toast.LENGTH_SHORT).show()
            dialog.dismiss() }

        dialog.show()
    }

    //----------------------------------------------------------------------------

}
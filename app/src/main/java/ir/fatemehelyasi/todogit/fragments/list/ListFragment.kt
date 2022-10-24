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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import ir.fatemehelyasi.todogit.R
import ir.fatemehelyasi.todogit.data.viewmodel.ToDoViewModel
import ir.fatemehelyasi.todogit.databinding.FragmentListBinding
import ir.fatemehelyasi.todogit.fragments.SharedViewModel

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val adapter: MyListAdapter by lazy { MyListAdapter() }

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater)

        //navigate between fragments
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }


        //recycler
        setUpRecyclerView()

        //viewModel
        // return list of data from dataclass database
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            //fun recycler
            mSharedViewModel.checkIfDatabaseEmpty(it)
            adapter.setData(it)
        })
        //empty DB
        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
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
                if (menuItem.itemId == R.id.menu_delete_all) {
                    confirmRemovalToAllData()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    //----------------------------------------------------------------------------setUpRecyclerView
    private fun setUpRecyclerView() {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())

        // swipe To Delete
        swipeToDelete(binding.recyclerview)
    }
    //----------------------------------------------------------------------------swipeToDelete
    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback=object :SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete=adapter.dataList[viewHolder.adapterPosition]
                mToDoViewModel.deleteData(itemToDelete)
                Toast.makeText(requireContext(),"Successfully Removed:'${itemToDelete.title}'",Toast.LENGTH_SHORT).show()
            }
        }
        val itemTouchHelper=ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    //----------------------------------------------------------------------------//sweet Alert Dialog
    private fun confirmRemovalToAllData() {
        val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)

        dialog.titleText = "Are you sure you want to remove everything?"
        dialog.confirmText = "Delete"
        dialog.cancelText = "Cancel"

        dialog.setCancelClickListener {
            dialog.dismiss()
            Toast.makeText(requireContext(), "Canceled to removed everything ", Toast.LENGTH_SHORT)
                .show()
        }

        dialog.setConfirmClickListener {
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(), "Removed everything ", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    //----------------------------------------------------------------------------
    fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {

            binding.noDataImageView.visibility = View.VISIBLE
            binding.noDataTextView.visibility = View.VISIBLE
        } else {
            binding.noDataImageView.visibility = View.INVISIBLE
            binding.noDataTextView.visibility = View.INVISIBLE
        }
    }
}
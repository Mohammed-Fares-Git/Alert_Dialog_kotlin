package com.example.mohammed_fares_kotlin_c2.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mohammed_fares_kotlin_c2.R
import com.example.mohammed_fares_kotlin_c2.databinding.ActivityMainBinding
import com.example.mohammed_fares_kotlin_c2.databinding.ConfirmDialogBinding
import com.example.mohammed_fares_kotlin_c2.databinding.PhoneDialogBinding
import com.example.mohammed_fares_kotlin_c2.model.SmartPhone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var dialogBinding: PhoneDialogBinding
    //lateinit var confirmDialogBinding: ConfirmDialogBinding

    val smartPhoneViewModel: SmartPhoneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight_NoActionBar)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        val smartPhoneAdapter = SmartPhoneAdapter(object : SmartPhoneAdapter.OnSmartPhoneClicked {
            override fun onClick(smartPhone: SmartPhone) {
                showCustomDialog(this@MainActivity,"Edit smrtphone","Edit",smartPhone) {
                    lifecycleScope.launch {
                        delay(500L)
                        val edit = async { smartPhoneViewModel.edit(it) }
                        if (edit.await() > 0) {
                            Toast.makeText(this@MainActivity, "success", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        } )

        /*

        val builder = AlertDialog.Builder(this)
        builder.setView(R.layout.phone_dialog)
        val dialog = builder.create()
        dialog.show()


        val smartPhone1 =SmartPhone(1,"hamza",8,"evff",false)
        val smartPhone2 =SmartPhone(2,"amine",8,"evff",true)
        val smartPhone3 =SmartPhone(3,"karim",8,"evff",false)
        val smartPhone4 =SmartPhone(4,"hnia",8,"evff",true)


        lifecycleScope.launch {
            smartPhoneViewModel.remove(smartPhone1)
            smartPhoneViewModel.remove(smartPhone2)
            smartPhoneViewModel.remove(smartPhone3)
            smartPhoneViewModel.remove(smartPhone4)
        }
        */


        binding.smartphoneList.apply {
            layoutManager = LinearLayoutManager(baseContext)
            adapter = smartPhoneAdapter
        }


        lifecycleScope.launch {
            smartPhoneViewModel.smaartPhoneStateFlow.collect {
                if (it.size > 0) {
                    smartPhoneAdapter.submitList(it)
                    binding.tvEmpty.visibility = View.GONE
                    binding.smartphoneList.visibility = View.VISIBLE
                } else {
                    smartPhoneAdapter.submitList(it)
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.smartphoneList.visibility = View.GONE
                }

            }
        }

        binding.btnAdd.setOnClickListener({
            showCustomDialog(MainActivity@this,"Add smrtphone","Add",null) {
                lifecycleScope.launch {
                    delay(500L)
                    smartPhoneViewModel.add(it)
                }
            }
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or  ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                 lifecycleScope.launch {
                     val remove = async { smartPhoneViewModel.remove(smartPhoneAdapter.getSmartPhone(viewHolder.adapterPosition)) }
                     if (remove.await() > 0) {
                         Toast.makeText(this@MainActivity, "success", Toast.LENGTH_SHORT).show()
                     } else {
                         Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_SHORT).show()
                     }

                 }
            }

        }).attachToRecyclerView(binding.smartphoneList)




    }

    /*
    fun showConfirmDialog(context: Context, smartPhone: SmartPhone, action: (smartPhone: SmartPhone)->Unit) {

        val inflater = LayoutInflater.from(context)
        confirmDialogBinding = ConfirmDialogBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        val view = confirmDialogBinding.root


        builder.setView(view)

        val dialog = builder.create()


        confirmDialogBinding.dialogAccept.setOnClickListener {
            action.invoke(smartPhone)
            dialog.dismiss()
        }
        dialog.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )


        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)


        dialog.show()
    }
    */

    fun showCustomDialog(context: Context, titel: String,btn: String, smartPhone: SmartPhone?, action: (smartPhone: SmartPhone)->Unit) {

        val inflater = LayoutInflater.from(context)
        dialogBinding = PhoneDialogBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        val view = dialogBinding.root


        builder.setView(view)

        val dialog = builder.create()

        dialogBinding.dialogTitel.text = titel
        dialogBinding.dialogBtn.text = btn

        smartPhone?.let {
            dialogBinding.dialogEtPhoneName.text = Editable.Factory.getInstance().newEditable(it.name)
            dialogBinding.dialogEtPhoneImageUrl.text = Editable.Factory.getInstance().newEditable(it.image)
            dialogBinding.dialogEtPhonePrix.text = Editable.Factory.getInstance().newEditable(it.price.toString())
            dialogBinding.dialogSwitchPf.isChecked = it.fingerPrint
        }

        dialogBinding.dialogBtn.setOnClickListener {
            var id: Int? = null
            smartPhone?.let {
                id = it.id
            }
            val name = dialogBinding.dialogEtPhoneName.text.toString()
            val price = try {
                dialogBinding.dialogEtPhonePrix.text.toString().toInt()
            } catch (e: Exception) {
                0
            }
            val image = dialogBinding.dialogEtPhoneImageUrl.text.toString()
            val pf = dialogBinding.dialogSwitchPf.isChecked

            var fieldRequire: Int = 0

            if (name.isBlank()) {
                fieldRequire++
                dialogBinding.dialogEtPhoneName.apply {
                    setBackgroundResource(R.drawable.et_err_bg)
                    setTextColor(resources.getColor(R.color.red))
                }
                Toast.makeText(this@MainActivity, "the name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (price.toString().isBlank() or (price <= 0)) {
                fieldRequire++
                dialogBinding.dialogEtPhonePrix.apply {
                    setBackgroundResource(R.drawable.et_err_bg)
                    setTextColor(resources.getColor(R.color.red))
                }
                Toast.makeText(this@MainActivity, "the price is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (image.isBlank()) {
                fieldRequire++
                dialogBinding.dialogEtPhoneImageUrl.apply {
                    setBackgroundResource(R.drawable.et_err_bg)
                    setTextColor(resources.getColor(R.color.red))
                }
                Toast.makeText(this@MainActivity, "the image url is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val smartPhoneFromInputs = SmartPhone(id,name,price,image,pf)
            action.invoke(smartPhoneFromInputs)
            dialog.dismiss()
        }
        /*
        dialog.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )
        */


        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)


        dialog.show()
    }
}
package dam.moviles.proyecto4_ejer4

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.snackbar.Snackbar
import dam.moviles.proyecto4_ejer4.databinding.FragmentEjercicio4Binding

//CLASE MONEDA
data class Moneda(val nombre:String,val valor:Double,val imagenViewRes:Int)

class Ejercicio4Fragment : Fragment() {
    private lateinit var binding : FragmentEjercicio4Binding
    private val listaMonedas:List<Moneda> = listOf(
        Moneda("Euro",1.0,R.drawable.unioneuropea),
        Moneda("Dólar",1.08,R.drawable.estadosunidos),
        Moneda("Libra",0.83,R.drawable.reinounido),
        Moneda("Yen",165.86,R.drawable.china)
        )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configurarBinding(inflater,container)
        configurarSpinners()
        configurarBoton()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun configurarBinding(inflater: LayoutInflater, container: ViewGroup?){
        binding = FragmentEjercicio4Binding.inflate(inflater, container, false)
    }
    private fun configurarSpinners() {
        val listaNombresMonedas:MutableList<String> = mutableListOf()
        val adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,listaNombresMonedas)
        for (i in listaMonedas){
            listaNombresMonedas.add(i.nombre)
        }
        binding.spnCambio.adapter = adapter
        binding.spnCambio.setSelection(0)
        binding.spnCambio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                val selectedItem = listaMonedas[position]
                binding.imageViewSpnCambio.setImageResource(selectedItem.imagenViewRes)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        binding.spnRecibir.adapter = adapter
        binding.spnRecibir.setSelection(1)
        binding.spnRecibir.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                val selectedItem = listaMonedas[position]
                binding.imageViewSpnRecibir.setImageResource(selectedItem.imagenViewRes)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun configurarBoton() {
        binding.btnConvertir.setOnClickListener({
            val dinero:Editable = binding.edtDinero.text
            val cambio:String = binding.spnCambio.selectedItem.toString()
            val recibir:String = binding.spnRecibir.selectedItem.toString()
            var monedaCambio:Moneda? = listaMonedas.find { it.nombre == cambio }
            var monedaRecibir:Moneda? = listaMonedas.find { it.nombre == recibir }

            if (monedaCambio != null && monedaRecibir!= null && dinero.isNotEmpty()) {
            val dineroConvertido = DecimalFormat("#.00").format((dinero.toString().toDouble()*monedaRecibir.valor/monedaCambio.valor))
            Snackbar.make(
                requireView(),
                "Conversión: "+dineroConvertido+" "+monedaRecibir.nombre,
                Snackbar.LENGTH_LONG
            ).show()
            }
        })
    }
}
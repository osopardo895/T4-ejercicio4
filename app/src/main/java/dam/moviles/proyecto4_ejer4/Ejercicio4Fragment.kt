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

class Ejercicio4Fragment : Fragment() {
    private lateinit var binding : FragmentEjercicio4Binding
    private val valorMonedas:Map<String,Double> = mapOf(
        "Euro" to 1.0,
        "Dólar" to 1.08,
        "Libra" to 0.83,
        "Yen" to 165.86
    )
    private val imagenMonedas:Map<String,Int> = mapOf(
        "Euro" to R.drawable.unioneuropea,
        "Dólar" to R.drawable.estadosunidos,
        "Libra" to R.drawable.reinounido,
        "Yen" to R.drawable.china
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
        for (i in valorMonedas.keys.toList()){
            listaNombresMonedas.add(i)
        }
        val adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,listaNombresMonedas)

        binding.spnCambio.adapter = adapter
        binding.spnCambio.setSelection(0)
        binding.spnCambio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                val selectedItem:Int = imagenMonedas.values.toList().get(position)
                binding.imageViewSpnCambio.setImageResource(selectedItem)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        binding.spnRecibir.adapter = adapter
        binding.spnRecibir.setSelection(1)
        binding.spnRecibir.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                val selectedItem:Int = imagenMonedas.values.toList().get(position)
                binding.imageViewSpnRecibir.setImageResource(selectedItem)
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
            var monedaCambio:Double? = valorMonedas.get(cambio)
            var monedaRecibir:Double? = valorMonedas.get(recibir)

            if (monedaCambio != null && monedaRecibir!= null && dinero.isNotEmpty()) {
            val dineroConvertido = DecimalFormat("#.00").format((dinero.toString().toDouble()*monedaRecibir/monedaCambio))
            Snackbar.make(
                requireView(),
                "Conversión: "+dineroConvertido+" "+recibir,
                Snackbar.LENGTH_LONG
            ).show()
            }
        })
    }
}
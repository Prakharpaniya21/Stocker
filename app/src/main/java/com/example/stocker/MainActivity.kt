package com.example.stocker
import StockAdapter
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var stockListView: ListView
    private lateinit var fab: FloatingActionButton
    private lateinit var stocks: MutableList<Stock>
    private lateinit var stockAdapter: StockAdapter
    private lateinit var stockSymbols: MutableList<String>
    private lateinit var stockRecyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        stockListView = findViewById(R.id.stockListView)
        stockRecyclerView = findViewById(R.id.stockRecyclerView)
        fab = findViewById(R.id.fab)

        stocks = mutableListOf(
            Stock("APL", 150.0),
            Stock("GOOGL", 2700.0),
            Stock("AMZN", 3400.0)
            // Add more mock data as needed
        )


        stockAdapter = StockAdapter(this, stocks)
        stockRecyclerView.adapter = stockAdapter // have to change stock adapter
        stockRecyclerView.layoutManager = LinearLayoutManager(this)
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                stocks.removeAt(position)
                stockAdapter.notifyItemRemoved(position)
            }
        })

        itemTouchHelper.attachToRecyclerView(stockRecyclerView)

        stockSymbols = stocks.map { it.symbol }.toMutableList()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stockSymbols)



        fab.setOnClickListener {
            // Show the BottomSheetFragment when FAB is clicked

            val alertDialog = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_add_stock, null)
            alertDialog.setView(dialogView)

            alertDialog.setPositiveButton("Add") { dialog, _ ->
                val symbolEditText = dialogView.findViewById<EditText>(R.id.symbolEditText)
                val priceEditText = dialogView.findViewById<EditText>(R.id.priceEditText)

                val symbol = symbolEditText.text.toString()
                val price = priceEditText.text.toString().toDoubleOrNull()

                if (symbol.isNotEmpty() && price != null) {
                    val newStock = Stock(symbol, price)
                    stocks.add(newStock)
                    stockSymbols.add(newStock.symbol)
                    adapter.notifyDataSetChanged()
                }

                dialog.dismiss()
            }

            alertDialog.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            alertDialog.create().show()
        }

        stockAdapter.setOnItemClickListener { position ->
            // Handle item click (e.g., show stock details in a bottom sheet).
            val selectedStock = stocks[position]
            val bottomSheetFragment = StockDetailBottomSheetFragment()
            val bundle = Bundle()
            bundle.putString("symbol", selectedStock.symbol)
            bundle.putDouble("price", selectedStock.price)
            bottomSheetFragment.arguments = bundle

            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

//
        stockListView.setOnItemLongClickListener { _, _, position, _ ->

            val selectedStock = stocks[position]
            Toast.makeText(this, "Selected Stock: ${selectedStock.symbol} is deleted from the list", Toast.LENGTH_SHORT).show()
            stocks.removeAt(position)
            stockAdapter.notifyDataSetChanged()
            true
        }
    }

}

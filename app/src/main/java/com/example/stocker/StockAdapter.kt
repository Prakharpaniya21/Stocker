import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stocker.R
import com.example.stocker.Stock

class StockAdapter(private val context: Context, private val stocks: MutableList<Stock>) :
    RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_stock, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stock = stocks[position]
        holder.stockNameTextView.text = stock.symbol
        holder.stockPriceTextView.text = "$${stock.price}"
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    private var onItemClick: ((Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClick = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(position)
                }
            }
        }

        val stockNameTextView: TextView = itemView.findViewById(R.id.stockNameTextView)
        val stockPriceTextView: TextView = itemView.findViewById(R.id.stockPriceTextView)

    }
}

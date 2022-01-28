package com.txl.abx.desafio.adaptadores

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.txl.abx.desafio.Detalles
import com.txl.abx.desafio.R
import com.txl.abx.practicamodulo3p3.dataclass.Book

class AdaptadorBooks(val activity: Activity,val books: MutableList<Book>): RecyclerView.Adapter<AdaptadorBooks.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book,parent,false)
        return BookHolder(view)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        val book = books.get(position)
        with(holder){
            tv_title.setOnClickListener{
                val bundle = Bundle()
                bundle.putSerializable("book",book)
                val intent = Intent(activity,Detalles::class.java)
                intent.putExtras(bundle)
                activity.startActivity(intent)
            }
            tv_title.text = book.attributes.title
            tv_content.text = book.attributes.content
            tv_type.text = book.type
            tv_link.text = book.links.self
        }
    }

    override fun getItemCount(): Int = books.size


    class BookHolder(view: View): RecyclerView.ViewHolder(view){
        val tv_title: TextView = view.findViewById(R.id.tv_title)
        val tv_content: TextView = view.findViewById(R.id.tv_content)
        val tv_type: TextView = view.findViewById(R.id.tv_type)
        val tv_link: TextView = view.findViewById(R.id.tv_link)
    }
}
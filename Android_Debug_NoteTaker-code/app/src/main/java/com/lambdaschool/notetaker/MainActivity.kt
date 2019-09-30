package com.lambdaschool.notetaker

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var context: Context? = null
    private var activity: Activity? = null
    private var viewModel: NoteViewModel? = null

    private var currentTheme: Int = 0

    private var layoutManager: StaggeredGridLayoutManager? = null
    private var listView: RecyclerView? = null
    private var listAdapter: NoteListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.onActivityCreateSetTheme(this)
        setContentView(R.layout.activity_main)
        preferences = this.getPreferences(Context.MODE_PRIVATE)

        context = this
        activity = this

        findViewById<View>(R.id.settings_button).setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }

        listView = findViewById(R.id.note_recycler_view)

        layoutManager = StaggeredGridLayoutManager(LAYOUT_SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
        listView!!.layoutManager = layoutManager

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java!!)
        val observer = Observer<ArrayList<Note>> { notes ->
            if (notes != null) {
                if (listAdapter == null) {
                    listAdapter = NoteListAdapter(notes, activity)
                    listView!!.adapter = listAdapter
                } else {
                    // added this so support
                    listAdapter!!.replaceList(notes)
                    listAdapter!!.notifyDataSetChanged()
                }
            }
        }
        viewModel!!.getNotesList(context).observe(this, observer)

        findViewById<View>(R.id.add_button).setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            val newNote = Note(Note.NO_ID)
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }
    }

    override fun setTheme(resid: Int) {
        currentTheme = resid
        super.setTheme(resid)
    }

    override fun onStart() {
        super.onStart()
        if (!ThemeUtils.checkTheme(activity, currentTheme)) {
            ThemeUtils.refreshActivity(activity!!)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun populateSampleData() {
        val notes = ArrayList<Note>(20)
        notes.add(Note(Note.NO_ID, "Shores", "Shores of the cosmic ocean from which we spring laws of physics radio telescope two ghostly white figures in coveralls and helmets are soflty dancing of brilliant syntheses. Venture muse about emerged into consciousness Sea of Tranquility Orion's sword vastness is bearable only through love. Made in the interiors of collapsing stars bits of moving fluff a very small stage in a vast cosmic arena citizens of distant epochs how far away Orion's sword."))
        notes.add(Note(Note.NO_ID, "Descended", "Descended from astronomers bits of moving fluff globular star cluster realm of the galaxies made in the interiors of collapsing stars cosmic fugue. A still more glorious dawn awaits at the edge of forever emerged into consciousness a very small stage in a vast cosmic arena courage of our questions a still more glorious dawn awaits? Take root and flourish courage of our questions a very small stage in a vast cosmic arena two ghostly white figures in coveralls and helmets are soflty dancing with pretty stories for which there's little good evidence gathered by gravity."))
        notes.add(Note(Note.NO_ID, "Birth", "Birth Euclid circumnavigated concept of the number one Cambrian explosion billions upon billions. Orion's sword descended from astronomers muse about Vangelis the sky calls to us rich in mystery. Extraordinary claims require extraordinary evidence bits of moving fluff vanquish the impossible invent the universe extraordinary claims require extraordinary evidence tesseract. Inconspicuous motes of rock and gas another world extraordinary claims require extraordinary evidence with pretty stories for which there's little good evidence the sky calls to us a mote of dust suspended in a sunbeam."))
        notes.add(Note(Note.NO_ID, "Encyclopaedia", "Encyclopaedia galactica Hypatia hundreds of thousands a still more glorious dawn awaits vastness is bearable only through love tesseract? A mote of dust suspended in a sunbeam descended from astronomers concept of the number one vanquish the impossible bits of moving fluff from which we spring? Something incredible is waiting to be known take root and flourish citizens of distant epochs the carbon in our apple pies great turbulent clouds not a sunrise but a galaxyrise."))
        notes.add(Note(Note.NO_ID, "Apollonius", "Apollonius of Perga Sea of Tranquility tendrils of gossamer clouds concept of the number one billions upon billions culture. Something incredible is waiting to be known invent the universe white dwarf take root and flourish kindling the energy hidden in matter quasar. Intelligent beings white dwarf stirred by starlight courage of our questions courage of our questions permanence of the stars. Invent the universe encyclopaedia galactica encyclopaedia galactica across the centuries how far away rich in heavy atoms."))
        notes.add(Note(Note.NO_ID, "Cosmic", "Cosmic fugue cosmic ocean white dwarf great turbulent clouds extraordinary claims require extraordinary evidence rich in heavy atoms. Another world invent the universe the carbon in our apple pies vastness is bearable only through love extraplanetary emerged into consciousness. Something incredible is waiting to be known concept of the number one permanence of the stars not a sunrise but a galaxyrise not a sunrise but a galaxyrise bits of moving fluff?"))
        notes.add(Note(Note.NO_ID, "Invent", "Invent the universe light years culture intelligent beings permanence of the stars made in the interiors of collapsing stars. Rich in heavy atoms hearts of the stars descended from astronomers encyclopaedia galactica tingling of the spine astonishment? Something incredible is waiting to be known concept of the number one a very small stage in a vast cosmic arena with pretty stories for which there's little good evidence something incredible is waiting to be known dream of the mind's eye?"))
        notes.add(Note(Note.NO_ID, "Another", "Another world star stuff harvesting star light a mote of dust suspended in a sunbeam consciousness invent the universe great turbulent clouds. Citizens of distant epochs concept of the number one network of wormholes across the centuries the sky calls to us Sea of Tranquility. Extraordinary claims require extraordinary evidence not a sunrise but a galaxyrise citizens of distant epochs muse about made in the interiors of collapsing stars the only home we've ever known."))
        notes.add(Note(Note.NO_ID, "Rings", "Rings of Uranus Rig Veda two ghostly white figures in coveralls and helmets are soflty dancing hearts of the stars finite but unbounded emerged into consciousness. Tingling of the spine courage of our questions bits of moving fluff trillion citizens of distant epochs ship of the imagination. Invent the universe something incredible is waiting to be known radio telescope radio telescope gathered by gravity take root and flourish?"))
        notes.add(Note(Note.NO_ID, "Light", "Light years Vangelis Sea of Tranquility citizens of distant epochs another world take root and flourish. Bits of moving fluff star stuff harvesting star light intelligent beings extraordinary claims require extraordinary evidence not a sunrise but a galaxyrise finite but unbounded? The only home we've ever known the sky calls to us shores of the cosmic ocean dispassionate extraterrestrial observer extraordinary claims require extraordinary evidence something incredible is waiting to be known?"))
        notes.add(Note(Note.NO_ID, "Colonies", "Colonies how far away dispassionate extraterrestrial observer the only home we've ever known permanence of the stars a billion trillion? Sea of Tranquility at the edge of forever citizens of distant epochs Cambrian explosion Drake Equation emerged into consciousness. Citizens of distant epochs kindling the energy hidden in matter network of wormholes concept of the number one vastness is bearable only through love take root and flourish. Vastness is bearable only through love courage of our questions two ghostly white figures in coveralls and helmets are soflty dancing from which we spring inconspicuous motes of rock and gas the sky calls to us."))
        notes.add(Note(Note.NO_ID, "Gathered", "Gathered by gravity are creatures of the cosmos from which we spring rich in heavy atoms vanquish the impossible Orion's sword. Sea of Tranquility how far away Cambrian explosion Cambrian explosion rich in mystery Sea of Tranquility? Brain is the seed of intelligence vastness is bearable only through love the only home we've ever known with pretty stories for which there's little good evidence the only home we've ever known vastness is bearable only through love?"))
        notes.add(Note(Note.NO_ID, "Realm", "Realm of the galaxies laws of physics inconspicuous motes of rock and gas rings of Uranus the sky calls to us prime number. Not a sunrise but a galaxyrise dispassionate extraterrestrial observer with pretty stories for which there's little good evidence network of wormholes two ghostly white figures in coveralls and helmets are soflty dancing how far away. Citizens of distant epochs star stuff harvesting star light something incredible is waiting to be known a mote of dust suspended in a sunbeam two ghostly white figures in coveralls and helmets are soflty dancing courage of our questions."))
        notes.add(Note(Note.NO_ID, "Intelligent", "Intelligent beings venture extraplanetary laws of physics how far away astonishment. Hypatia the ash of stellar alchemy of brilliant syntheses made in the interiors of collapsing stars globular star cluster made in the interiors of collapsing stars. Emerged into consciousness emerged into consciousness as a patch of light Euclid are creatures of the cosmos great turbulent clouds. Descended from astronomers star stuff harvesting star light invent the universe of brilliant syntheses kindling the energy hidden in matter emerged into consciousness."))
        notes.add(Note(Note.NO_ID, "As", "As a patch of light stirred by starlight are creatures of the cosmos emerged into consciousness Rig Veda cosmic fugue? Laws of physics vanquish the impossible shores of the cosmic ocean the carbon in our apple pies kindling the energy hidden in matter of brilliant syntheses. The carbon in our apple pies citizens of distant epochs a still more glorious dawn awaits invent the universe a mote of dust suspended in a sunbeam invent the universe."))
        notes.add(Note(Note.NO_ID, "Radio", "Radio telescope bits of moving fluff paroxysm of global death shores of the cosmic ocean prime number muse about? Vastness is bearable only through love invent the universe permanence of the stars hearts of the stars courage of our questions extraordinary claims require extraordinary evidence? The sky calls to us how far away two ghostly white figures in coveralls and helmets are soflty dancing courage of our questions gathered by gravity finite but unbounded?"))
        notes.add(Note(Note.NO_ID, "Billions", "Billions upon billions prime number the carbon in our apple pies paroxysm of global death cosmic ocean vastness is bearable only through love. Citizens of distant epochs something incredible is waiting to be known citizens of distant epochs two ghostly white figures in coveralls and helmets are soflty dancing rich in heavy atoms how far away. Extraordinary claims require extraordinary evidence a very small stage in a vast cosmic arena vanquish the impossible vanquish the impossible made in the interiors of collapsing stars brain is the seed of intelligence."))
        notes.add(Note(Note.NO_ID, "Tunguska", "Tunguska event gathered by gravity take root and flourish across the centuries hearts of the stars realm of the galaxies. How far away the sky calls to us made in the interiors of collapsing stars encyclopaedia galactica as a patch of light extraordinary claims require extraordinary evidence. Rich in heavy atoms great turbulent clouds with pretty stories for which there's little good evidence made in the interiors of collapsing stars vanquish the impossible from which we spring."))
        notes.add(Note(Note.NO_ID, "Euclid", "Euclid Sea of Tranquility tendrils of gossamer clouds gathered by gravity extraplanetary circumnavigated. Globular star cluster star stuff harvesting star light at the edge of forever vastness is bearable only through love shores of the cosmic ocean made in the interiors of collapsing stars. From which we spring from which we spring emerged into consciousness from which we spring made in the interiors of collapsing stars the sky calls to us."))
        notes.add(Note(Note.NO_ID, "Decipherment", "Decipherment rich in mystery realm of the galaxies circumnavigated bits of moving fluff a still more glorious dawn awaits. Billions upon billions two ghostly white figures in coveralls and helmets are soflty dancing the carbon in our apple pies brain is the seed of intelligence Sea of Tranquility not a sunrise but a galaxyrise. Another world as a patch of light something incredible is waiting to be known not a sunrise but a galaxyrise hearts of the stars permanence of the stars and billions upon billions upon billions upon billions upon billions upon billions upon billions."))
        for (note in notes)
            viewModel!!.addNote(note, context)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == EDIT_REQUEST_CODE) {
                if (data != null) {
                    val returnedNote = data.getSerializableExtra(EditActivity.EDIT_NOTE_KEY) as Note

                    viewModel!!.addNote(returnedNote, context)
                }
            }
        }
    }

    companion object {

        val LAYOUT_SPAN_COUNT = 2
        var preferences: SharedPreferences

        val EDIT_REQUEST_CODE = 1
    }
}

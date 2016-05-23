//package com.dstudios.pokermon;
//
//import android.app.ListActivity;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.view.ContextMenu;
//import android.view.ContextMenu.ContextMenuInfo;
//import android.view.Menu;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView.AdapterContextMenuInfo;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.SimpleCursorAdapter;
//
//public class StructureActivity extends ListActivity {
//	private EditText filterText = null;
//	SimpleCursorAdapter torneios;
//	Cursor niveis;
//	int qtdNiveis;
//	//Tournament torneioSelecionado;
//	//DbAdapterStructure dbAdapter;
//	//DbAdapterTournaments dbAdapterTournament;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_structure);
//
//		//dbAdapter = new DbAdapterStructure(this);
//		//dbAdapterTournament = new DbAdapterTournaments(this);
//		Bundle extras = getIntent().getExtras();
//		//torneioSelecionado = dbAdapterTournament.consultaTorneioPorId(extras
//		//		.getLong("torneio_selecionado"));
//		//fillData();
//		Button button = (Button) findViewById(R.id.add_nivel);
//		button.setOnClickListener(mAddNivelListener);
//		// button = (Button) findViewById(R.id.delete_round);
//		// button.setOnClickListener(mDeleteNivelListener);
//
//	}
//
//	View.OnClickListener mDeleteNivelListener = new OnClickListener() {
//		public void onClick(View v) {
//
//			// dbAdapter.deleteNivel());
//			fillData();
//		}
//
//	};
//	View.OnClickListener mAddNivelListener = new OnClickListener() {
//		public void onClick(View v) {
//			qtdNiveis++;
//			dbAdapter.insereNivel(qtdNiveis, 0, 0, 0,
//					Integer.valueOf(torneioSelecionado.getDuracaoPadrao()),
//					DbAdapterStructure.NORMAL, torneioSelecionado.getId());
//			fillData();
//		}
//
//	};
//
//	private void fillData() {
//		niveis = dbAdapter.consultaTournamentStructure(torneioSelecionado
//				.getId());
//		qtdNiveis = niveis.getCount();
//		String[] from = new String[] { "nivel", "small", "big", "ante", "time" };
//		int[] to = new int[] { R.id.nivel, R.id.edit_SB, R.id.edit_BB,
//				R.id.edit_Ante, R.id.edit_time };
//
//		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
//				R.layout.structure_row, niveis, from, to, 0);
//		ListView listView = getListView();
//		listView.setAdapter(adapter);
//	}
//
//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v,
//			ContextMenuInfo menuInfo) {
//		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
//		niveis.moveToPosition(info.position);
//		menu.setHeaderTitle(niveis.getString(1));
//		menu.add(Menu.NONE, 1, 1, "Delete");
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.menu_structure, menu);
//		return true;
//	}
//
//}

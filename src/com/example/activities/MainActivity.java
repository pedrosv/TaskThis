package com.example.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.example.taskthis.AdapterListView;
import com.example.taskthis.R;
import com.example.taskthis.Status;
import com.example.taskthis.Task;

public class MainActivity extends Activity {

	// Pacote para transferia de dados entre telas.
	private Bundle bundle;
	// ID temporario.
	private long idTemp;
	// Lista de todas as tarefas criadas.
	private List<Object> tasks;
	// Lista das tarefas exibidas na tela.
	private List<Task> tasksListView;

	// ==== Componentes da IU ==== //
	// Filtram as tarefas exibidas na tela.
	private CheckBox toDo, doing, done;
	// Lista de tarefas que sao exibidas na tela.
	private ListView listView;
	// Campo de texto para criacao de novas tarefas.
	private EditText description;
	private Button addButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();

		addListenerEditText();
		addListenerAddButton();
		addListenerListView();

		addListenerCheckBox(toDo, Status.TODO);
		addListenerCheckBox(doing, Status.DOING);
		addListenerCheckBox(done, Status.DONE);

	}

	private void addListenerAddButton() {
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (description.getText() == null
						|| description.getText().toString().equals("")) {
					return;
				}
				Task aux = new Task(description.getText().toString(), ++idTemp);
				description.getText().clear();
				tasks.add(aux);
				// Limpa o campo de texto.
				description.getText().clear();
				if (toDo.isChecked()) {
					tasksListView.add(aux);
				}
				listView.invalidateViews();
			}
		});

	}

	/**
	 * Adiciona o listener no campo de texto. Toda vez que a tecla ENTER é
	 * pressionada o método onkey é chamado.
	 */
	private void addListenerEditText() {
		description.setFocusableInTouchMode(true);
		description.requestFocus();
		description.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					if (description.getText() == null
							|| description.getText().toString().equals("")) {
						return false;
					}
					Task aux = new Task(description.getText().toString(),
							++idTemp);
					// Limpa o campo de texto.
					description.getText().clear();
					if (toDo.isChecked()) {
						tasksListView.add(aux);
					}
					listView.invalidateViews();
				}
				return false;
			}
		});
	}

	/**
	 * Adiciona o listener no checkbox passado como parametro. Quando o checkbox
	 * eh clicado o metodo onClick eh chamado e as lista de exibicao das tarefas
	 * eh atualizado.
	 * 
	 * @param checkBox
	 * @param list
	 *            Lista de tarefas com status correspondentes ao checkbox(to do,
	 *            doing ou done).
	 */
	private void addListenerCheckBox(final CheckBox checkBox,
			final Status status) {
		checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (checkBox.isChecked()) {
					addTasks(status);
				} else {
					removeTasks(status);
				}
				listView.invalidateViews();
			}
		});
	}

	private void removeTasks(Status status) {
		List<Task> auxList = new ArrayList<Task>(tasksListView);
		for (Task t : auxList) {
			if (t.getStatus().equals(status)) {
				tasksListView.remove(t);
			}
		}
	}

	private void addTasks(Status status) {
		for (Object t : tasks) {
			if (((Task) t).getStatus().equals(status)
					&& !tasksListView.contains(t)) {
				tasksListView.add((Task) t);
			}
		}
	}

	/**
	 * Adiciona o listener nos itens da lista. Quando um item eh pressionado a
	 * tela {@link TaskActivity} é criada e os dados (o item selecionado e o
	 * tasksInfo) sao enviados.
	 */
	private void addListenerListView() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Task item = ((AdapterListView) listView.getAdapter())
						.getItem(position);
				Intent it = new Intent(getBaseContext(), TaskActivity.class);
				it.putExtra("selected_task", item);
				it.putExtra("tasks", tasks.toArray());
				startActivity(it);
			}
		});
	}

	/*
	 * Inicializa as variaveis.
	 */
	private void init() {
		// Caso nao tenha dados para receber, bundle eh null.
		bundle = this.getIntent().getExtras();
		listView = (ListView) findViewById(R.id.list);

		// Houve transferencia de dados.
		if (bundle != null) {
			Object[] aux = (Object[]) this.getIntent().getSerializableExtra(
					"tasks");
			tasks = new ArrayList<Object>(Arrays.asList(aux));

		} else {
			// Nao houve transferencia de dados
			tasks = new ArrayList<Object>();

			// TODO TESTE lembrar de apagar depois
			Task task1 = new Task("Relatorio do projeto real", ++idTemp);
			Task task2 = new Task("Implementacao projeto novo", ++idTemp);
			Task task3 = new Task("Deixar projeto pra ultima hora", ++idTemp);

			task2.setStatus(Status.DOING);
			task3.setStatus(Status.DONE);

			tasks.add(task1);
			tasks.add(task2);
			tasks.add(task3);
			// FIM TESTE
		}
		idTemp = tasks.size();
		tasksListView = new ArrayList<Task>();
		for (Object o : tasks) {
			tasksListView.add((Task) o);
		}
		listView.setAdapter(new AdapterListView(this.getBaseContext(),
				tasksListView));

		description = (EditText) findViewById(R.id.newTask_editText);
		addButton = (Button) findViewById(R.id.add_button);

		toDo = (CheckBox) findViewById(R.id.todo_checkBox);
		doing = (CheckBox) findViewById(R.id.doing_checkBox);
		done = (CheckBox) findViewById(R.id.done_checkBox);

		toDo.setChecked(true);
		doing.setChecked(true);
		done.setChecked(true);
	}
}
package com.example.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.taskthis.R;
import com.example.taskthis.Status;
import com.example.taskthis.Task;

/**
 * Tela de detalhes/edicao de tarefas.
 * 
 */
public class TaskActivity extends Activity {

	// ==== Componentes da IU ==== //
	private EditText description;
	private RadioGroup radios;
	private RadioButton toDo;
	private RadioButton doing;
	private Button saveButton;

	// Dados transferidos(recebidos e depois enviados) quando ha mudanca de
	// tela.
	private Task task;
	private List<Object> tasks;

	// private TasksInfo tasksInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);

		init();
		// Altera o texto do editor de campo para a descricao da tarefa
		// recebida.
		description.setText(task.getDescription());
		// Seleciona o radio corresnpondente ao status da tarefa recebida.
		if (task.getStatus().equals(Status.TODO)) {
			radios.check(R.id.todo_radio);
		} else if (task.getStatus().equals(Status.DOING)) {
			radios.check(R.id.doing_radio);
		} else {
			radios.check(R.id.done_radio);
		}

		addListenerSaveButton();

	}

	/**
	 * Inicializa as variaveis.
	 */
	private void init() {
		description = (EditText) findViewById(R.id.description_edittext);
		radios = (RadioGroup) findViewById(R.id.status_radio);
		toDo = (RadioButton) findViewById(R.id.todo_radio);
		doing = (RadioButton) findViewById(R.id.doing_radio);
		saveButton = (Button) findViewById(R.id.save_button);

		// Recebe os dados enviados da classe MainActivity.
		task = (Task) this.getIntent().getSerializableExtra("selected_task");
		Object[] aux = (Object[]) this.getIntent()
				.getSerializableExtra("tasks");
		tasks = new ArrayList<Object>(Arrays.asList(aux));
	}

	/**
	 * Adiciona o listener do botao salvar.
	 */
	private void addListenerSaveButton() {
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (description.getText() == null
						|| description.getText().toString().replaceAll(" ", "")
								.isEmpty()) {
					return;
				}
				task.setDescription(description.getText().toString());

				if (toDo.isChecked()) {
					task.setStatus(Status.TODO);
				} else if (doing.isChecked()) {
					task.setStatus(Status.DOING);
				} else {
					task.setStatus(Status.DONE);
				}

				if (tasks.contains(task)) {
					tasks.remove(task);
					tasks.add(task);
				}
				// Envia de volta os dados recebidos.
				Intent it = new Intent(getBaseContext(), MainActivity.class);
				it.putExtra("tasks", tasks.toArray());
				startActivity(it);
			}
		});
	}
}
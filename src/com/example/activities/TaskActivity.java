package com.example.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.taskthis.R;
import com.example.taskthis.Status;
import com.example.taskthis.TaskManager;

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
	private RadioButton done;
	private Button saveButton;
	private Button deleteButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);

		init();
		// Altera o texto do editor de campo para a descricao da tarefa
		// recebida.
		description.setText(TaskManager.getInstance().getSelectedTask()
				.getDescription());
		// Seleciona o radio corresnpondente ao status da tarefa recebida.
		if (TaskManager.getInstance().getSelectedTask().getStatus()
				.equals(Status.TODO)) {
			radios.check(R.id.todo_radio);
		} else if (TaskManager.getInstance().getSelectedTask().getStatus()
				.equals(Status.DOING)) {
			radios.check(R.id.doing_radio);
		} else {
			radios.check(R.id.done_radio);
			doing.setVisibility(View.GONE);
			toDo.setVisibility(View.GONE);
		}

		addListenerSaveButton();
		addListenerDeleteButton();

	}

	/**
	 * Inicializa as variaveis.
	 */
	private void init() {
		description = (EditText) findViewById(R.id.description_edittext);
		radios = (RadioGroup) findViewById(R.id.status_radio);
		toDo = (RadioButton) findViewById(R.id.todo_radio);
		doing = (RadioButton) findViewById(R.id.doing_radio);
		done = (RadioButton) findViewById(R.id.done_radio);
		saveButton = (Button) findViewById(R.id.save_button);
		deleteButton = (Button) findViewById(R.id.delete_button);

		if (TaskManager.getInstance().amountDoing() >= 3
				&& TaskManager.getInstance().getSelectedTask().getStatus()
						.equals(Status.TODO)) {
			doing.setVisibility(View.GONE);
			done.setVisibility(View.GONE);
		}
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
				TaskManager.getInstance().getSelectedTask()
						.setDescription(description.getText().toString());

				if (toDo.isChecked()) {
					TaskManager.getInstance().getSelectedTask()
							.setStatus(Status.TODO);
				} else if (doing.isChecked()) {
					TaskManager.getInstance().getSelectedTask()
							.setStatus(Status.DOING);
				} else {
					TaskManager.getInstance().getSelectedTask()
							.setStatus(Status.DONE);
				}

				TaskManager.getInstance().refreshSelectedTask();
				finish();
			}
		});
	}

	private void addListenerDeleteButton() {
		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TaskManager.getInstance().removeSelectedTask();
				finish();
			}
		});
	}
}
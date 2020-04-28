import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class StudentSystem extends JFrame implements ActionListener{//gui interface������ ���� ����
	//�ʵ� ����(frame�� �� component ����, static ���� ����)
	JPanel lowerpanel;//lower panel
	CardLayout cards = new CardLayout();
	JPanel card_panel;
	
	//1.search
	JTextArea sch_ta_result;
	JTextField sch_tf_words;
	
	//2.insert
	JTextField is_tf_name;
	JRadioButton is_rb_female;
	JRadioButton is_rb_male;
	JTextField is_tf_department;
	JTextField is_tf_address;
	JTextField is_tf_age;
	JTextArea is_tf_result;
	
	//3.delete
	JTextField del_tf_words;
	JTextArea del_tf_result;
	
	//4.update
	JTextField up_tf_words;
	JTextField up_tf_name;
	JTextField up_tf_age;
	JRadioButton up_rb_female;
	JRadioButton up_rb_male;
	JTextField up_tf_department;
	JTextField up_tf_address;
	JTextArea up_tf_result;
	JButton cancel_button2;
	String up_sch_key="";//for hashmap
	
	//for student information
	static HashMap<String,StudentInfo> students;//key is student ID(hash map ����)
	static String filename = "StudentData.dat"; //���� �̸� ����
	
	static String name_o;
	static String age_o;
	static String gender_o;
	static String id_o;
	static String department_o;//update�� �ϱ� ���� ���� ����
	
	StudentSystem(){//frame ������
		setTitle("Student System");//frame title
		setSize(500,500);//frame size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�ݱ� â ������ ���α׷� ����
		
		//menu �����
		JMenuBar menu = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		JMenuItem menu1_1 = new JMenuItem("Save");
		menu1_1.addActionListener(this);
		menu1.add(menu1_1);
		JMenu menu2 = new JMenu("Edit");
		JMenuItem menu2_1 = new JMenuItem("Search");
		JMenuItem menu2_2 = new JMenuItem("Insert");
		JMenuItem menu2_3 = new JMenuItem("Delete");
		JMenuItem menu2_4 = new JMenuItem("Update");
		menu2_1.addActionListener(this);//action event handling
		menu2_2.addActionListener(this);
		menu2_3.addActionListener(this);
		menu2_4.addActionListener(this);
		menu2.add(menu2_1);
		menu2.add(menu2_2);
		menu2.add(menu2_3);
		menu2.add(menu2_4);
		menu.add(menu1);
		menu.add(menu2);
		add(menu,"North");
		
		//���� �ؿ� �򸮴� panel ����
		lowerpanel = new JPanel(new BorderLayout());
		JPanel upperpanel = new JPanel();//button�� ���� panel ����
		JButton button1 = new JButton("Search");
		JButton button2 = new JButton("Insert");
		JButton button3 = new JButton("Delete");
		JButton button4 = new JButton("Update");
		button1.addActionListener(this);//action event handling
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		upperpanel.add(button1);
		upperpanel.add(button2);
		upperpanel.add(button3);
		upperpanel.add(button4);
		lowerpanel.add(upperpanel, BorderLayout.PAGE_START);//button�� �ִ� panel�� ���� ��ܿ� ��ġ
		
		//button �Ʒ��� panel�� card layout���� ����
		card_panel = new JPanel(cards);
		
		//card layout panel�� �� search panel ����
		JPanel search_panel = new JPanel(new BorderLayout());
		JPanel search_panel_1 = new JPanel();
		sch_tf_words = new JTextField(20);
		search_panel_1.add(sch_tf_words);
		JButton button_go = new JButton("go");
		button_go.addActionListener(this);//action event handling
		search_panel_1.add(button_go);
		search_panel.add(search_panel_1,"North");//�Է� ���� text field�� action�� ������ button�� �ִ� panel
		JPanel search_panel_2 = new JPanel(new BorderLayout());//text area�� �� panel
		sch_ta_result = new JTextArea();//hash map���κ��� data �޾ƿ���
		Set s = students.keySet();
		Iterator<String> it = s.iterator();
		int i=0;
		while(it.hasNext()){
			sch_ta_result.append(students.get(it.next()).toString()+"\n");
		}
		sch_ta_result.setEditable(false);
		JScrollPane scroll = new JScrollPane(sch_ta_result);//scroll ����
		search_panel_2.add(scroll,"Center");
		search_panel.add(search_panel_2,"Center");
		card_panel.add(search_panel,"search");//search panel ��ü�� card layout�� panel�� �ø�
		
		//card layout panel�� �� insert panel ����
		JPanel insert_panel = new JPanel(new BorderLayout());
		JPanel insert_panel_1 = new JPanel(new GridLayout(6,0));//����ڷκ��� student information�� �Է� �޴� ��
		
		JPanel insert_panel_1_1 = new JPanel(new GridLayout(0,2));//name
		JLabel label1 = new JLabel("NAME");
		insert_panel_1_1.add(label1);
		is_tf_name = new JTextField(10);
		insert_panel_1_1.add(is_tf_name);
		insert_panel_1.add(insert_panel_1_1);
		
		JPanel insert_panel_1_2 = new JPanel(new GridLayout(0,2));//age
		JLabel label2 = new JLabel("AGE");
		insert_panel_1_2.add(label2);
		is_tf_age = new JTextField(10);
		insert_panel_1_2.add(is_tf_age);
		insert_panel_1.add(insert_panel_1_2);
		
		JPanel insert_panel_1_3 = new JPanel(new GridLayout(0,2));//gender
		JLabel label3 = new JLabel("GENDER");
		insert_panel_1_3.add(label3);
		JPanel button_panel = new JPanel();
		is_rb_female = new JRadioButton("Female",true);
		is_rb_male = new JRadioButton("Male");
		ButtonGroup bg = new ButtonGroup();
		bg.add(is_rb_female);
		bg.add(is_rb_male);
		button_panel.add(is_rb_male);
		button_panel.add(is_rb_female);
		insert_panel_1_3.add(button_panel);
		insert_panel_1.add(insert_panel_1_3);
		
		JPanel insert_panel_1_4 = new JPanel(new GridLayout(0,2));//id
		JLabel label4 = new JLabel("ID");
		insert_panel_1_4.add(label4);
		is_tf_address = new JTextField(10);
		insert_panel_1_4.add(is_tf_address);
		insert_panel_1.add(insert_panel_1_4);
		
		JPanel insert_panel_1_5 = new JPanel(new GridLayout(0,2));//department
		JLabel label5 = new JLabel("DEPARTMENT");
		insert_panel_1_5.add(label5);
		is_tf_department = new JTextField(10);
		insert_panel_1_5.add(is_tf_department);
		insert_panel_1.add(insert_panel_1_5);
		
		JPanel insert_panel_1_6 = new JPanel();//buttons
		JButton insert_button = new JButton("insert");
		insert_button.addActionListener(this);//action event handling
		insert_panel_1_6.add(insert_button);
		JButton cancel_button = new JButton("cancel");
		cancel_button.addActionListener(this);//action event handling
		insert_panel_1_6.add(cancel_button);
		insert_panel_1.add(insert_panel_1_6);
		
		JPanel insert_panel_2 = new JPanel(new BorderLayout());//text area
		is_tf_result = new JTextArea();//hash map���κ��� data �о����
		Set s1 = students.keySet();
		Iterator<String> it1 = s1.iterator();
		int i1=0;
		while(it1.hasNext()){
			is_tf_result.append(students.get(it1.next()).toString()+"\n");
		}
		is_tf_result.setEditable(false);
		JScrollPane scroll1 = new JScrollPane(is_tf_result);//scroll ����
		insert_panel_2.add(scroll1);
		
		insert_panel.add(insert_panel_1,"North");
		insert_panel.add(insert_panel_2,"Center");
		card_panel.add(insert_panel,"insert");//insert panel�� card layout�� �߰�
		
		//card layout panel�� �� delete panel ����
		JPanel delete_panel = new JPanel(new BorderLayout());
		JPanel delete_panel_1 = new JPanel();
		del_tf_words = new JTextField(20);
		delete_panel_1.add(del_tf_words);
		JButton button_del = new JButton("del");
		button_del.addActionListener(this);//action event handling
		delete_panel_1.add(button_del);
		delete_panel.add(delete_panel_1,"North");//�Է� ���� text field�� action�� ������ button�� �ִ� panel
		JPanel delete_panel_2 = new JPanel(new BorderLayout());//text area�� �� panel
		del_tf_result = new JTextArea();//hash map���� ���� data �о����
		Set s2 = students.keySet();
		Iterator<String> it2 = s2.iterator();
		int i2=0;
		while(it2.hasNext()){
			del_tf_result.append(students.get(it2.next()).toString()+"\n");
		}
		del_tf_result.setEditable(false);
		JScrollPane scroll2 = new JScrollPane(del_tf_result);//scroll ����
		delete_panel_2.add(scroll2);
		delete_panel.add(delete_panel_2,"Center");
		card_panel.add(delete_panel,"delete");//delete panel�� card layout�� �߰�
		
		//card layout panel�� �� update panel ����
		JPanel update_panel = new JPanel(new BorderLayout());
		JPanel update_panel_1 = new JPanel(new GridLayout(7,0));//����ڷκ��� student information�� �Է� �޴� ��
		
		JPanel update_panel_1_0 = new JPanel();
		up_tf_words = new JTextField(20);
		update_panel_1_0.add(up_tf_words);
		JButton button_go2 = new JButton("GO");
		button_go2.addActionListener(this);//action event handling
		update_panel_1_0.add(button_go2);
		update_panel_1.add(update_panel_1_0);//�Է� ���� text field�� action�� ������ button�� �ִ� panel
		
		JPanel update_panel_1_1 = new JPanel(new GridLayout(0,2));//name
		JLabel label6 = new JLabel("NAME");
		update_panel_1_1.add(label6);
		up_tf_name = new JTextField(10);
		update_panel_1_1.add(up_tf_name);
		update_panel_1.add(update_panel_1_1);
		
		JPanel update_panel_1_2 = new JPanel(new GridLayout(0,2));//age
		JLabel label7 = new JLabel("AGE");
		update_panel_1_2.add(label7);
		up_tf_age = new JTextField(10);
		update_panel_1_2.add(up_tf_age);
		update_panel_1.add(update_panel_1_2);
		
		JPanel update_panel_1_3 = new JPanel(new GridLayout(0,2));//gender
		JLabel label8 = new JLabel("GENDER");
		update_panel_1_3.add(label8);
		JPanel button_panel2 = new JPanel();
		up_rb_female = new JRadioButton("Female",true);
		up_rb_male = new JRadioButton("Male");
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(up_rb_female);
		bg2.add(up_rb_male);
		button_panel2.add(up_rb_male);
		button_panel2.add(up_rb_female);
		update_panel_1_3.add(button_panel2);
		update_panel_1.add(update_panel_1_3);
		
		JPanel update_panel_1_4 = new JPanel(new GridLayout(0,2));//id
		JLabel label9 = new JLabel("ID");
		update_panel_1_4.add(label9);
		up_tf_address = new JTextField(10);
		update_panel_1_4.add(up_tf_address);
		update_panel_1.add(update_panel_1_4);
		
		JPanel update_panel_1_5 = new JPanel(new GridLayout(0,2));//department
		JLabel label10 = new JLabel("DEPARTMENT");
		update_panel_1_5.add(label10);
		up_tf_department = new JTextField(10);
		update_panel_1_5.add(up_tf_department);
		update_panel_1.add(update_panel_1_5);
		
		JPanel update_panel_1_6 = new JPanel();//buttons
		JButton insert_button2 = new JButton("update");
		insert_button2.addActionListener(this);//action event handling
		update_panel_1_6.add(insert_button2);
		cancel_button2 = new JButton("cancel");
		cancel_button2.addActionListener(this);//action event handling
		update_panel_1_6.add(cancel_button2);
		update_panel_1.add(update_panel_1_6);
		
		JPanel update_panel_2 = new JPanel(new BorderLayout());//text area
		up_tf_result = new JTextArea();//hash map���κ���  data �о����
		Set s3 = students.keySet();
		Iterator<String> it3 = s3.iterator();
		int i3=0;
		while(it3.hasNext()){
			up_tf_result.append(students.get(it3.next()).toString()+"\n");
		}
		up_tf_result.setEditable(false);
		JScrollPane scroll3 = new JScrollPane(up_tf_result);//scroll ����
		update_panel_2.add(scroll3);
		
		update_panel.add(update_panel_1,"North");
		update_panel.add(update_panel_2,"Center");
		card_panel.add(update_panel,"update");//insert panel�� card layout�� �߰�
		
		lowerpanel.add(card_panel,"Center");//button�� card panel�� �Բ� �ִ� panel ��ü�� frame ���� �ø�
		add(lowerpanel, "Center");
		
		setVisible(true);
	}
	
	//action event handlnig
	@Override
	public void actionPerformed(ActionEvent e){
		StudentInfo[] students3 = new StudentInfo[50];//Ư�� student ��ü�� �Է��� �迭 ����
		int j;
		
		if(e.getActionCommand().equals("Search")){//search button�� �����ų� menu�� �������� ��
			cards.show(card_panel, "search");//card layout���� search �κ� ������
			sch_tf_words.setText("");
			sch_ta_result.setText("");//text area �ʱ�ȭ
			Set s = students.keySet();
			Iterator<String> it = s.iterator();
			int i=0;
			while(it.hasNext()){
				sch_ta_result.append(students.get(it.next()).toString()+"\n");
			}//���� hash map�� �ִ� ����Ʈ�� text area �ٽ� ä��
		}
		else if(e.getActionCommand().equals("Insert")){//insert button�� �����ų� menu�� �������� ��
			cards.show(card_panel, "insert");//card layout���� insert �κ� ������
			is_tf_name.setText("");
			is_rb_female.setSelected(true);
			is_tf_address.setText("");
			is_tf_department.setText("");
			is_tf_age.setText("");//�ʱ�ȭ
			is_tf_result.setText("");
			Set s = students.keySet();
			Iterator<String> it = s.iterator();
			int i=0;
			while(it.hasNext()){
				is_tf_result.append(students.get(it.next()).toString()+"\n");
			}//���� hash map�� �ִ� ����Ʈ�� text area �ٽ� ä��
		}
		else if(e.getActionCommand().equals("Delete")){//delete button�� �����ų� menu�� �������� ��
			cards.show(card_panel, "delete");//card layout���� delete �κ� ������
			del_tf_words.setText("");//�ʱ�ȭ
			del_tf_result.setText("");
			Set s = students.keySet();
			Iterator<String> it = s.iterator();
			int i=0;
			while(it.hasNext()){
				del_tf_result.append(students.get(it.next()).toString()+"\n");
			}//���� hash map�� �ִ� ����Ʈ�� text area �ٽ� ä��
		}
		else if(e.getActionCommand().equals("Update")){//card layout���� update �κ� ������
			cards.show(card_panel, "update");//card layout���� update �κ� ������
			up_tf_words.setText("");//�ʱ�ȭ
			up_tf_name.setText("");
			up_tf_age.setText("");
			up_rb_female.setSelected(true);
			up_tf_address.setText("");
			up_tf_department.setText("");
			up_tf_result.setText("");
			Set s = students.keySet();
			Iterator<String> it = s.iterator();
			int i=0;
			while(it.hasNext()){
				up_tf_result.append(students.get(it.next()).toString()+"\n");
			}//���� hash map�� �ִ� ����Ʈ�� text area �ٽ� ä��
		}
		
		//search ���� go button�� ������ �� action ����
		if(e.getActionCommand().equals("go")){
			String info = sch_tf_words.getText();//text field�κ��� string ����
			j=0;
			Set s = students.keySet();
			Iterator<String> it = s.iterator();
			while(it.hasNext()){//�迭�� ��ȸ�ϸ� ã�� Ư�� data�� �ִ��� Ȯ��
				StudentInfo student = new StudentInfo();
				student = students.get(it.next());
				if(info.equalsIgnoreCase(student.getName()) || info.equalsIgnoreCase(student.getID()) || info.equalsIgnoreCase(student.getDepartment())){
					students3[j] = new StudentInfo();
					students3[j++] = student;//data�� ������ ���ο� �迭�� ����
				}
			}
			if(j !=0){//���ο� �迭�� �ε����� 0�� �ƴ� ���
				sch_ta_result.setText("");
				for(int k=0; k<j; k++){
					sch_ta_result.append(students3[k].toString()+"\n");//data�� ��� text area�� ���
				}
			}
			else if(j==0){//���ο� �迭 �ε����� 0�� ���
				sch_ta_result.setText("No Result");
			}
			sch_tf_words.setText("");//�ʱ�ȭ
		}
		
		//insert ���� insert button�� ������ �� action ����
		if(e.getActionCommand().equals("insert")){
			String name = is_tf_name.getText();//��ü ���� �ޱ�
			char gender = 0;
			if(is_rb_female.isSelected()){
				gender='F';
			}
			else {
				gender='M';
			}
			String ID = is_tf_address.getText();
			String Department = is_tf_department.getText();
			String age_str = is_tf_age.getText();
			try{
				int age = Integer.parseInt(age_str);//string�� integer�� �ٲٱ�
				students.put(ID, new StudentInfo(name, gender, ID, Department, age));//��ü ������ ���ÿ� hash map�� ����
				is_tf_result.append(students.get(ID).toString()+"\n");//text area�� �߰�
				is_tf_name.setText("");
				is_rb_female.setSelected(true);
				is_tf_address.setText("");
				is_tf_department.setText("");
				is_tf_age.setText("");//�ʱ�ȭ
			}
			catch(Exception e1){//�Է��� ����� ���� ������ ���(age�� �̻��� ���� ������ ���)
				is_tf_name.setText("");
				is_rb_female.setSelected(true);
				is_tf_address.setText("");
				is_tf_department.setText("");
				is_tf_age.setText("");//�ʱ�ȭ
			}
		}
		
		//insert ���� cancel button�� ������ �� action ����
		if(e.getActionCommand().equals("cancel")){
			is_tf_name.setText("");
			is_rb_female.setSelected(true);
			is_tf_address.setText("");
			is_tf_department.setText("");
			is_tf_age.setText("");//�ʱ�ȭ
		}
		
		//delete ���� del button�� ������ �� action ����
		if(e.getActionCommand().equals("del")){
			String info = del_tf_words.getText();//Ư�� data�� �����ϱ� ���� key �� �ޱ�(id)
			if(info.equals(""))//���� ���� ���� ��� �ʱ�ȭ
				del_tf_result.setText("");
			else
				students.remove(info);//key������ Ư�� ��ü�� ����
			del_tf_result.setText("");//�ʱ�ȭ
			Set s = students.keySet();//hash map�� �ִ� ��ü�� ��� ����Ʈ
			Iterator<String> it = s.iterator();
			int i=0;
			while(it.hasNext()){
				StudentInfo student = new StudentInfo();
				student = students.get(it.next());
				del_tf_result.append(student.toString()+"\n");
			}
			del_tf_words.setText("");//�ʱ�ȭ
		}
		
		//update ���� GO button�� ������ �� action ����
		if(e.getActionCommand().equals("GO")){
			String info = up_tf_words.getText();//text field�κ��� string ����
			j=0;
			Set s = students.keySet();
			Iterator<String> it = s.iterator();
			while(it.hasNext()){//�迭�� ��ȸ�ϸ� ã�� Ư�� data�� �ִ��� Ȯ��
				StudentInfo student = new StudentInfo();
				student = students.get(it.next());
				if(info.equalsIgnoreCase(student.getName()) || info.equalsIgnoreCase(student.getID()) || info.equalsIgnoreCase(student.getDepartment())){
					students3[j] = new StudentInfo();
					students3[j++] = student;//data�� ������ ���ο� �迭�� ����
				}
			}
			if(j !=0){//���ο� �迭�� �ε����� 0�� �ƴ� ���
				up_tf_result.setText("");
				if(j==1){//id�� name���� data�� ã�� ��� data ���� �ϳ��̱� ������ text field�� �� �ε���
					up_tf_result.append(students3[0].toString()+"\n");//�˻��� ��� text area�� ����Ʈ
					up_tf_name.setText(students3[0].getName());//�˻��� ����� ��ü�� �̸� �Է�
					name_o = up_tf_name.getText();//������ �̸� ������ ������ ����
					int age = students3[0].getAge();//�˻��� ����� ��ü�� ���̸� ����
					up_tf_age.setText(Integer.toString(age));//���̸� string���� ��ģ �� �Է�
					age_o = up_tf_age.getText();//������ ���� ������ ������ ����
					char gender = students3[0].getGender();//gender�� char�� ����
					if(gender=='F'){//char�� ���� ����
						up_rb_female.setSelected(true);//radio button�� female�� ����
						gender_o = up_rb_female.getText();//���� gender�� string���� ����
					}
					else{
						up_rb_male.setSelected(true);//radio button�� male�� ����
						gender_o = up_rb_male.getText();//���� gender�� string���� ����
					}
					up_tf_address.setText(students3[0].getID());//�˻��� ��ü�� id�� �Է�
					id_o = up_tf_address.getText();//���� id�� ����
					up_tf_department.setText(students3[0].getDepartment());//�˻��� ��ü�� ���� �Է�
					department_o = up_tf_department.getText();//���� department�� ����
				}
				else{//department�� ã�� ��� data ���� �������� �����Ƿ� text area���� ���
					for(int k=0; k<j; k++){
						up_tf_result.append(students3[k].toString()+"\n");//data�� ��� text area�� ���
					}
				}
			}
			else if(j==0){//���ο� �迭 �ε����� 0�� ���
				up_tf_result.setText("No Result");
			}
		}
		
		//update ���� update button�� ������ �� action ����
		if(e.getActionCommand().equals("update")){
			String name = up_tf_name.getText();//���Ӱ� �Էµ� �̸� ����
			String age_str = up_tf_age.getText();//���Ӱ� �Էµ� ���� ����
			char gender=0;
			if(up_rb_female.isSelected()){//���Ӱ� ���õ� gender ����
				gender='F';
			}
			else
				gender='M';
			String id = up_tf_address.getText();//���Ӱ� �Էµ� id ����
			String department = up_tf_department.getText();//���Ӱ� �Էµ� ���� ����
			try{
				int age = Integer.parseInt(age_str);//age�� int������ �ٲٱ�
				students.put(id_o, new StudentInfo(name, gender, id, department, age));//������ key���� ���Ӱ� �Է� ���� data ����
				up_tf_result.setText(students.get(id_o).toString()+"\n");//����� hash map�� ������ �ٽ� text area�� ����
			}
			catch(Exception e3){//exception handling -> ��� �ʱ�ȭ(age�� �߸��� ���� ��� ���� ��)
				up_tf_words.setText("");
				up_tf_address.setText("");
				up_tf_name.setText("");
				up_tf_age.setText("");
				up_rb_female.setSelected(true);
				up_tf_department.setText("");
			}
		}
		
		//update ���� cancel button�� ������ �� action ����
		if(e.getSource()==cancel_button2){
			up_tf_name.setText(name_o);
			up_tf_age.setText(age_o);
			if(gender_o.equalsIgnoreCase("Female")){
				up_rb_female.setSelected(true);
			}
			else
				up_rb_male.setSelected(true);
			up_tf_address.setText(id_o);
			up_tf_department.setText(department_o);//������ ����� �ٽ� �ǵ���
		}
		
		//���� �����ϱ�
		if(e.getActionCommand().equals("Save")){
			try{
				ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
				outputStream.writeObject(students);
				outputStream.close();
			}
			catch(IOException e4){
				System.out.println("ERROR writing to file "+filename+".");
				System.exit(0);
			}//���Ӱ� ���� ����
			System.out.println("The file defined");
		}
	}
	
	public static void main(String[] args) {
		students = new HashMap<String,StudentInfo>();//hash map ����
		boolean no_file = false;//������ ���Ӱ� �������� ���� ����
		
		System.out.println("===============================");
		System.out.println("Name: Sung jihyun");
		System.out.println("ID: 1615030");
		System.out.println("Major: computer engineering");
		System.out.println("===============================");
		
		
		//������ ������ ���, �ִ� binary file�� �����ͼ� hash map�� ����
		try{
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename));
			students = (HashMap<String, StudentInfo>) inputStream.readObject();
			inputStream.close();
		}//���Ϸκ��� ��ü �о hash map�� ����
		catch(EOFException e){
			System.out.println("End of file exception.");
		}
		catch(FileNotFoundException e){
			System.out.println("File not found. Need to generate.");
			no_file=true;//������ ���Ӱ� �����ϱ� ���ؼ� boolean�� ����
		}
		catch(IOException e){
			System.out.print("IO Exception");
		}
		catch(Exception e){
			System.out.println("Exception");
		}//exception handling
		
		if(no_file==true){//������ ���� ��� file not found exception�� ���ؼ� if���� ����� �� �ְ� ��
			students.put("48544567", new StudentInfo("Callia", 'F', "48544567", "Economics",20)); 
			students.put("65001422", new StudentInfo("Eldora", 'F', "65001422", "Materials Engineering",21)); 
			students.put("78538007", new StudentInfo("Henry", 'M', "78538007", "Computer Science",22)); 
			students.put("35647567", new StudentInfo("Kenneth", 'F', "35647567", "Computer Science",25)); 
			students.put("23458211", new StudentInfo("Martha", 'M', "23458211", "Materials Engineering",28)); 
			students.put("63559848", new StudentInfo("Ralph", 'M', "63559848", "Materials Engineering",23)); 
			students.put("32544311", new StudentInfo("Huey", 'M', "32544311", "Korean Language and Literature",24)); 
			students.put("23396621", new StudentInfo("Remy", 'F', "23396621", "Architecture",20)); 
			students.put("43253688", new StudentInfo("Sasha", 'F', "43253688", "Architecture",22)); 
			students.put("65479987", new StudentInfo("Tess", 'M', "65479987", "Architecture",23)); 
			students.put("85251544", new StudentInfo("Velika", 'F', "85251544", "Korean Language and Literature",25)); 
			students.put("41996755", new StudentInfo("Zelia", 'F', "41996755", "Electronics Engineering",23)); 
			students.put("26144539", new StudentInfo("Brian", 'M', "26144539", "Electronics Engineering",22)); 
			students.put("18538799", new StudentInfo("Sylvia ", 'F', "18538799", "Economics",21)); 
			students.put("60110022", new StudentInfo("Tyler", 'M', "60110022", "Electronics Engineering",20)); 
			students.put("65382543", new StudentInfo("Simon", 'M', "65382543", "Computer Science",23)); 
			students.put("83564337", new StudentInfo("Primo", 'M', "83564337", "Electronics Engineering",22)); 
			students.put("60150991", new StudentInfo("Patricia", 'F', "60150991", "Computer Science",21)); 
			students.put("31796819", new StudentInfo("Olivia", 'F', "31796819", "Computer Science",24)); 
			students.put("08679009", new StudentInfo("Paul", 'M', "08679009", "Korean Language and Literature",22));

			try{
				ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
				outputStream.writeObject(students);
				outputStream.close();
			}
			catch(IOException e){
				System.out.println("ERROR writing to file "+filename+".");
				System.exit(0);
			}//������ �������� ���� ��� binary file ����
			System.out.println("The file is generated");
		}
		
		StudentSystem s = new StudentSystem();//��ü ����

	}

}

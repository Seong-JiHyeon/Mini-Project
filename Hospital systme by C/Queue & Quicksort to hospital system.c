#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#define SWAP(x,y,t) ( (t)=(x), (x)=(y), (y)=(t) )//sorting�� �ϱ� ���� swap
#define MAX_NAME_SIZE 50
#define MAX_GENDER 50
#define MAX_INFORMATION_SIZE 1000
#define MAX_SIZE 1000//ȯ�� ������ ���� ����ü�� �迭 ũ��

typedef struct{
	char name[MAX_NAME_SIZE];//ȯ�� �̸� �Է� ���� char �迭
	char gender[MAX_GENDER];//ȯ���� ������ �Է� ���� char �迭
	char clinic_history[MAX_INFORMATION_SIZE];//ȯ���� ������ �Է� ���� char �迭
}element;//�� ������ type�� element�� ����

typedef struct QueueNode{
   element item;
   struct QueueNode *link;
}QueueNode;//ť ���(ť�� ���� list�� ����)

typedef struct {
   QueueNode *front, *rear;
}QueueType;//���Ḯ��Ʈ�� ����� ť�� ���۰� ���� ��Ÿ��

element list[MAX_SIZE];//�湮�� ȯ�ڸ� �迭�� ����. ��, ȯ���� database�� ��.(���� list)
int num;//�湮�� ȯ�� �� ǥ��

int compare(element e1, element e2){
	return strcmp(e1.name, e2.name);
}//�Է� ���� �̸��� ���ĺ����� ������ ���� 1,0,-1�� ��Ÿ��

int partition(element list[], int left, int right){//quick sort�� �ϱ� ���� ���� ���� �� partition
	element pivot, temp;
	int low, high;//���� ����
	low = left;//list�� ���� ����
	high = right+1;//list�� ���� ������
	pivot = list[left];//���� ���� ������ ����

	do{
		do
			low++;
		while(compare(list[low], pivot)<0);//���� low �κ��� ȯ�� �̸��� ���ĺ��� pivot���� ���̸� -1�� return��(�� ȯ�ڴ� �迭���� �ڸ��� �ű� �ʿ� ����)
		do
			high--;
		while(compare(list[high], pivot)>0);//���� high �κ��� ȯ�� �̸��� ���ĺ��� pivot���� �ڸ� 1�� return��(�� ȯ�� ���� �迭���� �ڸ��� �ű� �ʿ� ����)

		if(low<high) SWAP(list[low], list[high], temp);//���� pivot�ڸ��� �������� �߸��� ��ġ�� �ִ� ��츦 ã������ swap
	}while(low<high);//�迭���� low�� high�� ��ġ�� �ٲ�� ������ ����
	SWAP(list[left], list[high], temp);//do-while���� ���� ���ͼ� swap(�迭�� ���� ���ʿ� �ִ� ����-pivot�� ���� high��ġ�� �ִ� ������ ���� �ٲ�) 
	return high;//high���� return
}

void quick_sort(element list[], int left, int right){//quick sort ����
	if(left<right){//left�� �� ũ�� �Ǹ� quick sort ����
		int q=partition(list, left, right);//partition���� ���ĵǰ� high���� ����
		quick_sort(list, left, q-1);//��ȯ������ quick sort �Լ� ȣ��(high�� �պκ� �迭)
		quick_sort(list, q+1, right);//(high�� �޺κ� �迭)
	}
}

void insert(element e){
	if(num < (MAX_SIZE)){
		list[num++] = e;
	}
}//ȯ���� ������ �迭�� �Է��ϴ� �Լ�

void print(){
	int i;
	for(i=0; i<num; i++){
		printf("%s : %s, %s\n", list[i].name, list[i].gender, list[i].clinic_history);
	}
}//�迭�� �ִ� ȯ�ڸ� ��� ����Ʈ

void error(char *message){
   fprintf(stderr, "%s\n", message);
   exit(1);
}//������ �߻����� ��� �޼��� ����Ʈ�ϰ� ����

void init(QueueType *q){
   q->front = q->rear = NULL;
}//ť�� �ʱ�ȭ

int is_empty(QueueType *q){
   return (q->front == NULL);
}//ť�� ��������� ���ؼ� return

void enqueue(QueueType *q, element item){//ť�� ������ �����ϴ� �Լ�
   QueueNode *temp = (QueueNode *) malloc (sizeof (QueueNode));//ť ��� ����
   if(temp == NULL)//temp�� �Ҵ���� �ʾ��� ���
      error("�޸𸮸� �Ҵ��� �� �����ϴ�.");
   else{
      temp->item = item;//ȯ�� �Է�
      temp->link = NULL;//ť���� ������ ����̱� ������ link�� ����Ű�� ���� ����
      if(is_empty(q)){//ť�� ����ִ� ���
         q->front = temp;
         q->rear = temp;//front�� rear ������ ��� temp�� ����Ŵ
      }
      else{//ť�� ������� ���� ���
         q->rear->link = temp;//ť�� rear�� ����Ű�� ������ link�� temp�� ����Ŵ
         q->rear = temp;//ť�� rear�� ���� ���� �������� temp�� ����Ŵ
      }
   }
}

element dequeue(QueueType *q){//ť���� ���� �ϳ��� ���� �Լ�
   QueueNode *temp = q->front;//ť���� front�� ����Ű�� �ִ� ��带 temp�� ����Ŵ
   element item;
   if(is_empty(q))//ť�� ����� ���
      error("Error.");
   else{
      item = temp->item;//temp�� ����Ű�� ��带 item�� ���� 
      q->front=q->front->link;//ť�� front�� ���� front�� ����Ű�� ����� link�� ����Ű�� ��带 ����Ű�� ��
      if(q->front == NULL)//�ٲ� front�� ����Ű�� ���� ������
         q->rear = NULL;//rear�� ����Ű�� �� ����(��, ť�� �����)
      free(temp);//�Ҵ��� temp �޸𸮿��� ����
      return item;//���� item ��带 return
   }
}

element peek(QueueType *q){//ť�� ���� �տ� �ִ� ��带 ��ȯ(ť���� ���� ���� �ƴ�)
   if(is_empty(q))//ť�� ����� ���� error
      error("Error.");
   else 
      return q->front->item;//ť�� front�� ����Ű�� ��� ��ȯ
}

void add_clinic_history(element *patient){//ȯ�ڰ� ��湮 ���� ��� ���� ����� ��� �߰��ؼ� ����ϵ��� �ϴ� �Լ� 
	int i=0, count=0;
	char sick[50];//���� ����
	while(patient->clinic_history[i++]!='\0'){//clinic_history �迭�� char�� ������ ������ �̵�
		count++;//count���� ����(�迭�� ���° index���� char�� �ִ��� ���
	}
	patient->clinic_history[count++]=',';//���� ������ �ֱ� ���� comma �ֱ�
	patient->clinic_history[count++]=' ';//��� ����
	gets(sick);//���� �Է� ����
	i=0;//i�� �ٽ� 0���� �ʱ�ȭ
	while(sick[i]!='\0'){//�Է¹��� ������ �ٽ� clinic_history �迭�� �ű�
		patient->clinic_history[count++]=sick[i++];//clinic_history�� �տ� �Էµ� char ���� ���� �Է� �ް�, sick�� �迭 ó������ char �Ѱ���
	}
	patient->clinic_history[count]='\0';//�� �Է� ���� �Ŀ� ���� string�� �����ٴ� ǥ�ø� �־���
}

void menu(){//�޴� ����Ʈ �Լ�
	printf("************************************\n");
	printf("1. Patient arrive at hospital\n");
	printf("2. Doctor Examine patient\n");
	printf("3. All patient list\n");
	printf("4. The hospital closed\n");
	printf("************************************\n");
}

void main(){
	FILE *fp;//���Ͽ��� �Է¹ޱ� ���� ����
   QueueType q;//ť ����
   element patient;//ȯ�� �Է¹ޱ� ���� element ����
   int i=0,n=0, information=0, run=1;
   char empty[10];//���� ����

   init(&q);//ť �ʱ�ȭ
   fp = fopen("name_data.txt", "r");//���� �б� �������� �ޱ�


   do{//����ڰ� ���� ������ ���α׷� ����
      menu();//�޴� ����Ʈ
      scanf("%d", &n);//�޴� ����

      switch(n){
      case 1://ȯ�� ���� �湮
		  fscanf(fp, "%s", patient.name);//���Ϸκ��� ȯ���̸� �� �� �Է¹ޱ�
		  if(feof(fp)){//���Ͽ� ���� ������ ��쿡�� 1�� �޴� ���̻� ������ �� ����
			  printf("No more patient will visit hospital.\n");
			  break;
		  }
		  printf("%s visited the hospital.\n", patient.name);//���� ������ �湮�ߴ��� ����Ʈ
		  enqueue(&q, patient);//ť�� ȯ�� ����
         break;
      case 2://ȯ�� ����
		  information=0;//������ �湮�� �� �ִ��� Ȯ���� �� setting�ϴ� ����
		  if(is_empty(&q)){//ť�� ����� ��� break
			  printf("There are no patient waiting for doctor.\n");
			  break;
		  }
		  patient = dequeue(&q);//��� ���� ȯ�ڸ� �� �� �θ�(ť���� ����)
		  for(i=0; i<num; i++){//���ſ� ������ �� �� �ִ��� �˻�('list'���� ���� �̸��� �ִ��� ��)
			  if(strcmp(list[i].name,patient.name)==0){
				  information=1;//1�� set
				  break;//ã���� �� �̻� search���� �ʰ� break
			  }
		  }
		  if(information==1){//���ſ� �湮�ߴ� ���
			   printf("%s : %s/ %s\n", list[i].name, list[i].gender, list[i].clinic_history);//ȯ�� ������ ������ ���� ��� ����Ʈ
			   gets(empty);//buffer���� enter�� �ޱ� ����
			   puts("Why patient is sick?");
			   add_clinic_history(&list[i]);//ȯ�� ���� �Է�
		  }
		  else{//���ſ� �湮���� ���� ���
			  printf("%s is first visit.\n", patient.name);
			  printf("What is the patient's gender?\n");
			  scanf("%s", patient.gender);//ȯ�� ���� �Է�
			  gets(empty);//buffer�� enter�� �ޱ� ����
			  puts("Why patient is sick?");
			  gets(patient.clinic_history);//���� �Է�
			  insert(patient);//list�� ȯ�� �߰�
			  quick_sort(list,0,num-1);//quick sort�� ���ĺ� ������ ����
		  }
		  break;
      case 3://list�� �ִ� ��� ȯ���� ���� ����Ʈ
		  if(num==0){//list�� ����ִ� ��� 
			  printf("There are no patient information.\n");
			  break;
		  }
		  print();//��� ����Ʈ�ϴ� �Լ� ȣ��
		  break;
      case 4://���� �� ����
		  printf("The hospital was closed\n");
		  while(!is_empty(&q)){//��� ���̴� ȯ�ڵ��� ������ ��������
			  patient = dequeue(&q);
			  printf("%s goes to home.\n", patient.name);
		  }
		  run=0;//���α׷� ����
		  break;
      default://�ٸ� �޴��� �Է����� ��
         printf("Error(No such menu)\n");
		 break;
      }
	  printf("\n");
   }while(run!=0);
   fclose(fp);//file �ݱ�

}
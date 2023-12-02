#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<windows.h>
#include<time.h>
#include<unistd.h>
 
#define TRUE 1
#define FALSE 0
#define OK  1
#define ERROR 0
#define OVERFLOW -1
typedef int Status;
typedef int ElemType;
 
 
typedef struct
{
 char Sweather[10];   //天气状况
 double Htem;     //最高温度 
 double Ltem;  //最低温度 
 double Stem;     //温度
 double Shum;     //湿度
}area;
typedef struct node
{
 int num;      //城市编号
 area weather;     //地区天气
 char name[5];      //地名
 struct node *next;    
  
}*LinkList,LNode;   //定义结构体
 
 
int NUM=0;     //全局变量 计算节点数目 
 
Status CreatList_L(LinkList &L,int n);      //信息录入 
void Print_LinkList( LinkList H);       //查看信息 
int login();            //登录
Status Delete_LinkList(LinkList &H);      //删除节点 
Status GetElem_L(LinkList &L);       //查找 
Status changeElem_L(LinkList &L);      //修改 
int Clearlist(LinkList &L);       //整表删除，
 
void welcome(LinkList &L);       //welcome   
void menu2(LinkList &L);        //管理员菜单 
void menu1(LinkList &L);        //用户菜单 
void printf_node(node*p);        //输出节点 
void my_return(LinkList &L);       //return 
void clothes(node *p);        //穿衣指数 
void save_node(LinkList &L,int i);     //保存节点到文件
void read_node(LinkList &L) ;      //从文件读取 
int Register();          //注册 
int signin();          //登录 
 
 
 
 
void read_node(LinkList &L)       //从文件读取 
{ 
 LNode *p,*pr;
 int i,n;
 pr=L;
  
 FILE *fp;
 if((fp=fopen("weatherinformation.txt","r"))==NULL)
 {
 printf("打开文件失败");
 exit(0);
 }
 
 fscanf(fp,"%d",&n); //从文件读出节点数目 
  
 for(i=1;i<=n;i++)
 {
 p=(LinkList)malloc(sizeof(LNode));
  fscanf(fp,"%d",&p->num);
 fscanf(fp,"%s",p->name);
 fscanf(fp,"%s",p->weather.Sweather);
 fscanf(fp,"%lf%lf",&p->weather.Ltem,&p->weather.Htem);
 p->weather.Stem=(p->weather.Htem+p->weather.Ltem)/2;
 fscanf(fp,"%lf",&p->weather.Shum);
  p->next=NULL;
 
 while(pr->next!=NULL){
  pr=pr->next;
 } 
 
 p->next=pr->next;
 pr->next=p;
 pr=pr->next;
  NUM++;
 }
 fclose(fp);
}
 
 
void save_node(LinkList &L)      //保存到文件 
{ 
  
 LNode *p,*pr;
 pr=L; 
 FILE *fp;
 if((fp=fopen("weatherinformation.txt","w"))==NULL)
 {
 printf("打开文件失败");
 exit(0);
 }
 fprintf(fp,"%d\n",NUM);
 while(pr->next!=NULL)
 {
 p=pr->next;
 fprintf(fp,"%d ",p->num);
  fprintf(fp,"%s ",p->name);
 fprintf(fp,"%s ",p->weather.Sweather);
 fprintf(fp,"%lf %lf ",p->weather.Ltem,p->weather.Htem);
 fprintf(fp,"%lf\n",p->weather.Shum);
 pr=pr->next;  
 }
 printf("保存成功！！！"); 
 fclose(fp);
}
 
 
 
int Register()        //注册 
{
 FILE* fp;
 char name[10],passworld[10];
  
 if((fp=fopen("demo.txt","a"))==NULL)
 {
 printf("打开文件失败");
 exit(0); 
 }
 printf("请输入用户名:");
 scanf("%s",name);
 fprintf(fp,"%s ",name);
 printf("请输入密码：");
 scanf("%s",passworld);
 fprintf(fp,"%s\n",passworld);
 fclose(fp);
 printf("注册成功!"); 
 return OK;
}
int signin()         //登录 
{ FILE* fp;
 char name[10],passworld[10];    //用户输入的账户密码 
 char name1[10],passworld1[10];   //从文件读取的账户和密码 
 int flag=0;
  
 if((fp=fopen("demo.txt","r"))==NULL)
 {
 printf("打开文件失败");
 exit(0);
 }
  
 printf("输入用户名:");
 scanf("%s",name);
 printf("输入密码:");
 scanf("%s",passworld); 
  
 while(!flag&&!feof(fp))
 { 
 fscanf(fp,"%s",name1);
 fscanf(fp,"%s",passworld1);
 if(strcmp(name,name1)==0&&strcmp(passworld,passworld1)==0)
 flag=1;
 }
 fclose(fp);
  
 return flag;
}
int login()            //login
{
 char f;
 int H;
 char a[10],ah[10],am[10];
 char b[10],bh[10],bm[10];
 FILE *fp;
 
 printf("1.注册！\n");
 printf("2.登录！\n");
 scanf("%d",&H);
 if(H==1)
 {
 Register();
  
 while(1){ 
 printf("是否登录？y or n\n");
 getchar();
 scanf("%c",&f);
 system("cls");
  
 if(f=='n')
 exit(0);
 else if(f=='y')
 { 
 if(signin())
 {
  printf("登录成功！\n");
  return OK;
 }
 else
 printf("密码或者用户错误!\n"); 
 }
  
 }
}
else if(H==2)
 while(1){
  
 if(signin())
 {
  printf("登录成功！\n");
  return OK;
 }
 else
 printf("密码或者用户错误!\n");
  
 } 
 return 0;
}
void clothes(node *p)          //穿衣指数 
{
  
 if(p->weather.Stem>=28)
 printf("夏季着装：轻棉织物制作的短衣、短裙、薄短裙、短裤\n\n");
 else if(p->weather.Stem>=24&&p->weather.Stem<28)
 printf("夏季着装：棉麻面料的衬衫、薄长裙、薄T恤\n\n");
 else if(p->weather.Stem>=21&&p->weather.Stem<24)
 printf("春秋过渡装：单层棉麻面料的短套装、T恤衫、薄牛仔衫裤、休闲服、职业套装\n\n");
 else if(p->weather.Stem>=18&&p->weather.Stem<21)
 printf("春秋过渡装：套装、夹衣、风衣、休闲装、夹克衫、西装、薄毛衣\n\n");
 else if(p->weather.Stem>=15&&p->weather.Stem<18)
 printf("春秋着装：风衣、大衣、夹大衣、外套、毛衣、毛套装、西装、防寒服\n\n");
 else if(p->weather.Stem>=11&&p->weather.Stem<15)
 printf("秋冬着装：毛衣、风衣、毛套装、西服套装\n\n");
 else if(p->weather.Stem>=6&&p->weather.Stem<11)
 printf("冬季着装：棉衣、冬大衣、皮夹克、厚呢外套、呢帽、手套、羽绒服、皮袄\n\n");
 else
 printf("冬季着装：棉衣、冬大衣、皮夹克、厚呢外套、呢帽、手套、羽绒服、皮袄\n\n");
 
}
Status CreatList_L(LinkList &L)        /* 信息录入 */
 {
  
 LNode *p,*pr;
 int i,n;
 printf("请输入地区数");
 scanf("%d",&n);
  
 pr=L;
 for(i=n;i>0;i--)
 {
  p=(LinkList)malloc(sizeof(LNode));
  printf("请输入城市编号\n");
 scanf("%d",&p->num);
 printf("请输入地名\n");
  scanf("%s",p->name);
 printf("请输入该地区的天气情况\n");
 scanf("%s",p->weather.Sweather);
 printf("最低温度和最高温度\n"); 
 scanf("%lf%lf",&p->weather.Ltem,&p->weather.Htem);
 p->weather.Stem=(p->weather.Htem+p->weather.Ltem)/2;
 printf("相对湿度\n");
 scanf("%lf",&p->weather.Shum);
 p->next=NULL;
 while(pr->next!=NULL){
  pr=pr->next;
 } 
 
 p->next=pr->next;
 pr->next=p;
 pr=pr->next;
 NUM++; 
 }
  
 return OK;
 }
void printf_node(node*p)            //输出一个节点 
{
  printf("%s的天气为：%s\n",p->name,p->weather.Sweather);
 printf("最低温度为：%.2lf度，最高温度为：%.2lf度，平均温度为：%.2lf度\n",p->weather.Ltem,p->weather.Htem,p->weather.Stem);
 printf("相对湿度为：%.1lf%%\n",p->weather.Shum);
 clothes(p); 
}
void my_return(LinkList &L,int n)         //my_return
{
 char f;
 while(1){
 
 printf("是否返回菜单？ y or n\n");
 getchar();
 scanf("%c",&f);
 if((f=='y'||f=='Y'))
 {
  if(n==1)
  menu1(L);
  else if(n==2)
  menu2(L);
 }
   
  
 else if(f=='n'||f=='N')
 {
 printf("bye-bye");
 exit(0);
 }
 }
}
void Print_LinkList( LinkList H)  /* 输出链式表 */
{
 LNode *p;
 p=H->next;
 if(p==NULL){
 printf("没有存入数据\n");
 return ;
 }
  
 while(p!=NULL) 
 {
  printf_node(p);
  
 p=p->next;
 }
printf("\n");
 }
 
Status Delete_LinkList(LinkList &H) /* 删除表序号为i的记录 */
 {
  
 int i;
  int j=1;
  LNode *p,*pr;
  p=H;
  if(NUM==0){
 printf("没有存入数据\n");
 return 0;
 }
  printf("请输入城市编号\n");
  scanf("%d",&i);
  while(p&&i!=p->next->num)
  {
   p=p->next;
    
  }
  if(!p) {
  printf("没有这个城市\n");
  return ERROR;}
  pr=p->next;
  p->next=pr->next;
  free(pr);
  printf("删除成功！\n");
  NUM--;
  return OK;
   
}/* Delete_LinkList */
 
  
int Clearlist(LinkList &L)    //整表删除，
 {
 LNode *p,*q;
 system("cls");
 printf("请确认你是否已经获得最高权限？y or n\n");
 getchar();
 getchar();
 printf("ERROR！你没有最高权限！");
 exit(0); 
 p=L->next;
 while(p)
 {
 q=p->next ;
 free(p);
 p=q;
 NUM--;
 }
 L->next=NULL;
 save_node(L); 
 return 1;
 } 
Status GetElem_L(LinkList &L)    //查找
{
 int num;
 char a[5]; 
 int j=1;
 printf("请输入城市名称\n");
 scanf("%s",&a); 
 LinkList p;
 p=L->next;
 while(p&&strcmp(p->name,a)!=0)
 {
  p=p->next;
   
 }
 if(!p){
  printf("没有这个城市！");
 return ERROR; 
 }
  
 printf_node(p);
 return OK;
}
Status changeElem_L(LinkList &L)           //修改 
{
 int num;
 int j=1;
 printf("请输入城市编号\n");
 scanf("%d",&num); 
 LinkList p;
 p=L->next;
 while(p&&num!=p->num)
 {
  p=p->next;
   
 }
 if(!p){printf("没有这个城市！\n");
 return ERROR;
 }
 printf_node(p);
  
  printf("请修改：\n");
 printf("请输入地名\n");
  scanf("%s",p->name);
  printf("请输入城市编号\n");
 scanf("%d",&p->num);
 printf("请输入该地区的天气情况\n");
 scanf("%s",p->weather.Sweather);
 printf("最高温度和最低温度\n"); 
 scanf("%lf%lf",&p->weather.Htem,&p->weather.Ltem);
 p->weather.Stem=(p->weather.Htem+p->weather.Ltem)/2;
 printf("相对湿度\n");
 scanf("%lf",&p->weather.Shum);
  
 printf("修改后的信息为：\n");
 printf_node(p);
 return OK;
}
void menu1(LinkList &L)            //用户菜单
{
 int choose;
 system("cls");
        
 printf("\n\n");
 printf("\t\t\t****************用户菜单****************\n");
 printf("\t\t\t*          *\n");
 printf("\t\t\t*   祝您使用愉快    *\n");
 printf("\t\t\t*          *\n");
 printf("\t\t\t* 查找信息请按 1 -- 全览请按 2  *\n");
 printf("\t\t\t*   退出请按 3    *\n");
 printf("\t\t\t*          *\n");
 printf("\t\t\t*          *\n");
 printf("\t\t\t****************************************\n");
 printf("\n\t\t\t\t 进入管理员菜单请按0\n");
 scanf("%d",&choose);
 
 switch(choose)
 {
 case 0:menu2(L);break;
 case 1:GetElem_L(L); my_return(L,1);break;
 case 2:Print_LinkList(L); my_return(L,1);break;
 case 3:exit(0) ;break; 
 default:menu1(L);  
 }
}
void menu2(LinkList &L)             //管理员菜单 
{
 int choose;
 system("cls");
  
 printf("\n\n");
 printf("\t\t\t****************管理员菜单****************\n");
 printf("\t\t\t*          *\n");
 printf("\t\t\t*  信息录入请按 1 -- 删除信息请按 2 *\n");
 printf("\t\t\t*  信息查找请按 3 -- 信息修改请按 4 *\n");
 printf("\t\t\t*  查看信息请按 5 -- 保存并退出按 6 *\n");
 printf("\t\t\t*          *\n");
 printf("\t\t\t*  清除所有数据按8     *\n");
 printf("\t\t\t*          *\n");
 printf("\t\t\t******************************************\n");
 printf("\n\t\t\t\t 进入用户菜单请按0\n");
 scanf("%d",&choose);
 
 switch(choose)
 {
  case 0:menu1(L);break;
 case 1:CreatList_L(L) ;my_return(L,2);break;
 case 2:Delete_LinkList(L) ;my_return(L,2);break;
 case 3:GetElem_L(L) ;my_return(L,2);break;
 case 4:changeElem_L(L); my_return(L,2);break;
 case 5:Print_LinkList(L); my_return(L,2);break;
 case 6:save_node(L);exit(0);
 case 8:Clearlist(L);break;
 default :menu2(L);
 }
}
void welcome(LinkList &L)            //welcome
{ 
 int a;
 login();
 printf("用户登录请按1\n");
 printf("管理员登录请按2\n");
 while(3>2)
 {
 scanf("%d",&a);
 if(a==1)
 { menu1(L);
 break;
 }
 else if(a==2)
 {
 menu2(L);
 break;
 }
 else
 {
 printf("输入错误！请重新输入\n");
 continue;
 }
 }
}
 
 
 
int main()  //main
{
 int i=0;
 LinkList L;
 L=(LNode*)malloc(sizeof(LNode));
 L->next=NULL;
 printf("\n\n\n\n\t\t\t正在从文件中读取数据请稍后");
 read_node(L);
 while(i<3)
 { printf(".");
 sleep(1);
 i++;
 }
 printf("\n\n\t\t\t读取成功！！\n");
 system("pause"); 
 system("cls");
 welcome(L);
 return 0;
}

using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using System.Xml.Linq;

namespace WpfApp1
{
	/// <summary>
	/// Логика взаимодействия для Test.xaml
	/// </summary>
	public partial class Test : Window
	{
		Users user; 
		Tests test;
		string[] userAnswers;
		public Test(Users _user, Tests _test)
		{
			InitializeComponent();
			user = _user;
			test = _test;
			_name.Content = user.Name;
			_surname.Content = user.Surname;
			if (user.isTeacher)
			{
				_isTeacher.Content = "Учитель";
			}
			else _isTeacher.Content = "Ученик";
			
			using (TestSystemEntities db = new TestSystemEntities())
			{

				string qLine = (from s in db.Tests
				 join b in db.Questions on s.QuestionsID equals b.ID where s.ID == test.ID
				 select b.QustionsLine).FirstOrDefault();
				string [] qLines = qLine.Split(new string[] { ";;" }, StringSplitOptions.None);

				List<string> questionTexts = new List<string>();
				List<string> answersTexts = new List<string> ();
				userAnswers = new string[qLines.Length];

				int[] ansTypes = new int[qLines.Length];

				for (int i = 0; i < qLines.Length; i++)
				{
					var tmp = Convert.ToInt32(qLines[i]);

					questionTexts.Add((from x in db.Question where x.ID == tmp select x.QuestionText).FirstOrDefault());
					answersTexts.Add((from x in db.Question where x.ID == tmp select x.AllAnswers).FirstOrDefault());
					ansTypes[i] = (from x in db.Question where x.ID == tmp select x.AnswerTypeID).FirstOrDefault();


					Button but = new Button();
					but.Name = string.Format("Question_{0}", i);
					but.Margin = new Thickness(150 + (10 * (i*4)), 10, 10, 10);
					but.Width = 30;
					but.Height = 30;
					but.Content = (i + 1).ToString();
					but.HorizontalAlignment = HorizontalAlignment.Left;
					but.VerticalAlignment = VerticalAlignment.Top;
					but.Click += (s, e) => {

						Label label = new Label();
						label.Margin = new Thickness(150, 40, 10, 10);
						label.Name = "qLabel";
						label.Content = questionTexts[Convert.ToInt32(but.Name.Remove(0, 9))];
						
						TestGrid.Children.Remove((Label)LogicalTreeHelper.FindLogicalNode(TestGrid, "qLabel"));
						TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, "UserAnswer"));
						TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, "SaveAns"));

						for (int j  = 0; j < 20; j++)
						{
							TestGrid.Children.Remove((RadioButton)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("Option_{0}", j)));
							TestGrid.Children.Remove((CheckBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("BoxOption_{0}", j)));
						}
						

						TestGrid.Children.Add(label);
						if (ansTypes[Convert.ToInt32(but.Name.Remove(0, 9))] == 1)
						{
							TextBox textBox = new TextBox();
							textBox.Margin = new Thickness(150, 70, 10, 10);
							textBox.Width = 200;
							textBox.Height = 20;
							textBox.HorizontalAlignment = HorizontalAlignment.Left;
							textBox.VerticalAlignment = VerticalAlignment.Top;
							textBox.Name = "UserAnswer";
							TestGrid.Children.Add(textBox);

							Button AnsBut = new Button();
							AnsBut.Margin = new Thickness(150, 100, 10, 10);
							AnsBut.Width = 130;
							AnsBut.Height = 30;
							AnsBut.Content = "Сохранить ответ";
							AnsBut.Name = "SaveAns";
							AnsBut.HorizontalAlignment = HorizontalAlignment.Left;
							AnsBut.VerticalAlignment = VerticalAlignment.Top;

							AnsBut.Click += (As, Ae) =>
							{
								userAnswers[Convert.ToInt32(but.Name.Remove(0, 9))] = ((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, "UserAnswer")).Text;
								
							};
							TestGrid.Children.Add(AnsBut);
						}
						if (ansTypes[Convert.ToInt32(but.Name.Remove(0, 9))] == 2)
						{
							string[] CAIanswers = answersTexts[Convert.ToInt32(but.Name.Remove(0, 9))]
							.Split(new string[] { ":;:" }, StringSplitOptions.None);

							for (int j = 0; j < CAIanswers.Length; j++) 
							{
								RadioButton rbut = new RadioButton();
								rbut.Name = string.Format("Option_{0}", j +1);
								rbut.Margin = new Thickness(150, 70 + (10 * (j * 3)), 10, 10);
								rbut.Width = 150;
								rbut.Height = 30;
								rbut.Content = CAIanswers[j];
								rbut.HorizontalAlignment = HorizontalAlignment.Left;
								rbut.VerticalAlignment = VerticalAlignment.Top;
								TestGrid.Children.Add(rbut);

							}

							Button AnsBut = new Button();
							AnsBut.Margin = new Thickness(150, 100 + (10 * (CAIanswers.Length - 1) * 3), 10, 10);
							AnsBut.Width = 130;
							AnsBut.Height = 30;
							AnsBut.Content = "Сохранить ответ";
							AnsBut.Name = "SaveAns";
							AnsBut.HorizontalAlignment = HorizontalAlignment.Left;
							AnsBut.VerticalAlignment = VerticalAlignment.Top;

							AnsBut.Click += (As, Ae) =>
							{
								string ans = "";
								foreach (var a in TestGrid.Children)
								{
									if (a is RadioButton && ((RadioButton)a).IsChecked == true)
									{
										ans = ((RadioButton)a).Name.Remove(0, 7);
									}
								}
								userAnswers[Convert.ToInt32(but.Name.Remove(0, 9))] = ans;
								
							};
							TestGrid.Children.Add(AnsBut);
						}
						if (ansTypes[Convert.ToInt32(but.Name.Remove(0, 9))] == 3)
						{
							string[] CAIanswers = answersTexts[Convert.ToInt32(but.Name.Remove(0, 9))]
							.Split(new string[] { ":;:" }, StringSplitOptions.None);

							for (int j = 0; j < CAIanswers.Length; j++)
							{
								CheckBox cbox = new CheckBox();
								cbox.Name = string.Format("BoxOption_{0}", j + 1);
								cbox.Margin = new Thickness(150, 70 + (10 * (j * 3)), 10, 10);
								cbox.Width = 150;
								cbox.Height = 30;
								cbox.Content = CAIanswers[j];
								cbox.HorizontalAlignment = HorizontalAlignment.Left;
								cbox.VerticalAlignment = VerticalAlignment.Top;
								TestGrid.Children.Add(cbox);

								
							}

							Button AnsBut = new Button();
							AnsBut.Margin = new Thickness(150, 100 + (10 * (CAIanswers.Length - 1) * 3), 10, 10);
							AnsBut.Width = 130;
							AnsBut.Height = 30;
							AnsBut.Content = "Сохранить ответ";
							AnsBut.Name = "SaveAns";
							AnsBut.HorizontalAlignment = HorizontalAlignment.Left;
							AnsBut.VerticalAlignment = VerticalAlignment.Top;

							AnsBut.Click += (As, Ae) =>
							{
								string ans = "";
								foreach (var a in TestGrid.Children)
								{
									if (a is CheckBox && ((CheckBox)a).IsChecked == true)
									{
										ans += ((CheckBox)a).Name.Remove(0, 10) + ";;";
									}
								}
								
								if (ans != "")
									ans = ans.Remove(ans.Length-2, 2);
								
								userAnswers[Convert.ToInt32(but.Name.Remove(0, 9))] = ans;
								
							};
							TestGrid.Children.Add(AnsBut);
						}

						
					};
					TestGrid.Children.Add(but);

				}
			}
				
		}

		private void Exit_Click(object sender, RoutedEventArgs e)
		{
			Window window = new Testing(user);
			window.Show();
			this.Close();
		}

		private void EndTest_Click(object sender, RoutedEventArgs e)
		{
			using (TestSystemEntities db = new TestSystemEntities())
			{
				string qLine = (from s in db.Tests
								join b in db.Questions on s.QuestionsID equals b.ID
								where s.ID == test.ID
								select b.QustionsLine).FirstOrDefault();
				string[] qLines = qLine.Split(new string[] { ";;" }, StringSplitOptions.None);
				string[] corrAns = new string[qLines.Length];
				for (int i = 0; i < qLines.Length; i++)
				{
					var tmp = Convert.ToInt32(qLines[i]);

					corrAns[i] = (from s in db.Question where s.ID == tmp select s.AnswerLine).FirstOrDefault();
					corrAns[i] = corrAns[i].Trim();
				}

				int correctCount = 0;

				for (int i = 0;i < corrAns.Length; i++)
				{
					if (userAnswers[i] == corrAns[i])
					{
						correctCount++;
						
					} 
					
				}

				db.CompletedTests.Add(new CompletedTests { UserCompID = user.ID, TestCompID = test.ID, Result = string.Format(correctCount + " / " + qLines.Length)});
				db.SaveChanges();

				Window window = new BaseWindow(user);
				window.Show();
				this.Close();
			}

		}
	}
}

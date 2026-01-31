using System;
using System.Collections.Generic;
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
using System.Xml.Serialization;

namespace WpfApp1
{
	/// <summary>
	/// Логика взаимодействия для NewTest.xaml
	/// </summary>
	public partial class NewTest : Window
	{
		Users user = new Users();
		public NewTest(Users _user)
		{
			InitializeComponent();
			user = _user;
		}


		List<Question> questionList = new List<Question>();
		private void AddQuest_Click(object sender, RoutedEventArgs e)
		{
			RemoveAll();

			Button button = new Button();
			button.Margin = new Thickness(145 + (12 *(questionList.Count * 3)), 38, 10, 10);
			button.Name = string.Format("Question_{0}", questionList.Count);
			button.Content = questionList.Count + 1;
			button.Width = 30;
			button.Height = 30;
			button.VerticalAlignment = VerticalAlignment.Top;
			button.HorizontalAlignment = HorizontalAlignment.Left;
			button.IsEnabled = false;

			button.Click += (Qs, Qe) =>
			{
				RemoveAll();

				AddButton.IsEnabled = true;

				Label label = new Label();
				label.Margin = new Thickness(145, 65, 10, 10);
				label.Name = "qLabel";
				label.Content = questionList[Convert.ToInt32(button.Name.Remove(0, 9))].QuestionText;
				TestGrid.Children.Add(label);

				if (questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerTypeID == 1)
				{
					TextBox textBoxA = new TextBox();
					textBoxA.Margin = new Thickness(145, 90, 10, 10);
					textBoxA.Width = 200;
					textBoxA.Height = 20;
					textBoxA.HorizontalAlignment = HorizontalAlignment.Left;
					textBoxA.VerticalAlignment = VerticalAlignment.Top;
					textBoxA.Name = "AnswerLine";
					if (questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine == "" || questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine == null)
					{
						textBoxA.Text = "Введите сюда правильный ответ";
					}
					else textBoxA.Text = questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine;
					textBoxA.PreviewTextInput += (Ts, Te) =>
					{
						if (textBoxA.Text == "Введите сюда правильный ответ")
						{
							textBoxA.Text = "";
						}
					};
					TestGrid.Children.Add(textBoxA);

					Button buttonSA = new Button();
					buttonSA.Margin = new Thickness(360, 90, 10, 10);
					buttonSA.Name = "SaveAnswer";
					buttonSA.Content = "Сохранить ответ";
					buttonSA.Width = 120;
					buttonSA.Height = 30;
					buttonSA.HorizontalAlignment = HorizontalAlignment.Left;
					buttonSA.VerticalAlignment = VerticalAlignment.Top;
					buttonSA.Click += (SAs, SAe) =>
					{
						questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine = 
						((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, "AnswerLine")).Text;
						RemoveAll();


					};
					TestGrid.Children.Add(buttonSA);

				}
				if (questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerTypeID == 2)
				{
					if(questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine != "" &&
					questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine != null)
					{
						string[] CAIanswers = questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AllAnswers
							.Split(new string[] { ":;:" }, StringSplitOptions.None);

						for (int j = 0; j < CAIanswers.Length; j++)
						{
							RadioButton rbut = new RadioButton();
			
							rbut.Name = string.Format("Option_{0}", j + 1);
							if (rbut.Name == string.Format("Option_{0}", questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine))
							{
								rbut.IsChecked = true;
							}
							rbut.Margin = new Thickness(150, 90 + (10 * (j * 2)), 10, 10);
							rbut.Width = 150;
							rbut.Height = 30;
							rbut.Content = CAIanswers[j];
							rbut.HorizontalAlignment = HorizontalAlignment.Left;
							rbut.VerticalAlignment = VerticalAlignment.Top;
							TestGrid.Children.Add(rbut);

						}
						int answersCount = CAIanswers.Length;

						Button buttonM = new Button();
						buttonM.Margin = new Thickness(145, 90 + (answersCount * 2*10), 10, 10);
						buttonM.Name = "MinusOption";
						buttonM.Content = "-";
						buttonM.Width = 20;
						buttonM.Height = 20;
						buttonM.VerticalAlignment = VerticalAlignment.Top;
						buttonM.HorizontalAlignment = HorizontalAlignment.Left;


						Button buttonP = new Button();
						buttonP.Margin = new Thickness(170, 90 + (answersCount * 2 * 10), 10, 10);
						buttonP.Name = "PlusOption";
						buttonP.Content = "+";
						buttonP.Width = 20;
						buttonP.Height = 20;
						buttonP.VerticalAlignment = VerticalAlignment.Top;
						buttonP.HorizontalAlignment = HorizontalAlignment.Left;
						buttonP.Click += (Ps, Pe) =>
						{
							buttonP.IsEnabled = false;
							answersCount++;

							buttonM.Margin = new Thickness(145, buttonM.Margin.Top + 20, 10, 10);
							buttonP.Margin = new Thickness(170, buttonP.Margin.Top + 20, 10, 10);

							RadioButton radioButton = new RadioButton();
							radioButton.VerticalAlignment = VerticalAlignment.Top;
							radioButton.HorizontalAlignment = HorizontalAlignment.Left;
							radioButton.Margin = new Thickness(145, buttonP.Margin.Top - 20, 10, 10);
							radioButton.Name = string.Format("Option_{0}", answersCount);
							radioButton.Width = 100;
							radioButton.Height = 20;
							TestGrid.Children.Add(radioButton);

							TextBox textBoxAT = new TextBox();
							textBoxAT.Margin = new Thickness(radioButton.Margin.Left + 20, radioButton.Margin.Top, 10, 10);
							textBoxAT.Width = 200;
							textBoxAT.Height = 15;
							textBoxAT.VerticalAlignment = VerticalAlignment.Top;
							textBoxAT.HorizontalAlignment = HorizontalAlignment.Left;
							textBoxAT.Name = string.Format("AnswerText_{0}", answersCount);
							TestGrid.Children.Add(textBoxAT);


							Button SA = new Button();
							SA.Margin = new Thickness(385, radioButton.Margin.Top, 10, 10);
							SA.Name = string.Format("SA");
							SA.Content = "Сохранить";
							SA.Width = 80;
							SA.Height = 20;
							SA.VerticalAlignment = VerticalAlignment.Top;
							SA.HorizontalAlignment = HorizontalAlignment.Left;
							SA.Click += (r, t) =>
							{
								((RadioButton)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("Option_{0}", answersCount))).Content =
							((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount))).Text;
								TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount)));
								TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("SA")));
								buttonP.IsEnabled = true;

							};
							TestGrid.Children.Add(SA);

						};

						buttonM.Click += (Ms, Me) =>
						{
							if (buttonM.Margin.Top > 90)
							{
								buttonP.IsEnabled = true;


								buttonM.Margin = new Thickness(145, buttonM.Margin.Top - 20, 10, 10);
								buttonP.Margin = new Thickness(170, buttonP.Margin.Top - 20, 10, 10);

								TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount)));
								TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("SA")));

								TestGrid.Children.Remove((RadioButton)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("Option_{0}", answersCount)));
								answersCount--;
							}


						};

						TestGrid.Children.Add(buttonP);
						TestGrid.Children.Add(buttonM);
						Button saveCorrAns = new Button();
						saveCorrAns.Margin = new Thickness(500, 90, 10, 10);
						saveCorrAns.Name = "SaveAnswer";
						saveCorrAns.Content = "Сохранить ответ";
						saveCorrAns.Width = 120;
						saveCorrAns.Height = 30;
						saveCorrAns.HorizontalAlignment = HorizontalAlignment.Left;
						saveCorrAns.VerticalAlignment = VerticalAlignment.Top;
						saveCorrAns.Click += (SAs, SAe) =>
						{
							string ans = "";
							string ansText = "";
							foreach (var a in TestGrid.Children)
							{
								if (a is RadioButton)
								{
									ansText += ((RadioButton)a).Content + ":;:";
								}
								if (a is RadioButton && ((RadioButton)a).IsChecked == true)
								{
									ans = ((RadioButton)a).Name.Remove(0, 7);
								}
							}
							if (ansText != "" && ansText != null)
								ansText = ansText.Remove(ansText.Length - 3, 3);
							questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine = ans;
							questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AllAnswers = ansText;
							RemoveAll();

						};
						TestGrid.Children.Add(saveCorrAns);
					}
					else
					{
						int answersCount = 0;

						Button buttonM = new Button();
						buttonM.Margin = new Thickness(145, 90, 10, 10);
						buttonM.Name = "MinusOption";
						buttonM.Content = "-";
						buttonM.Width = 20;
						buttonM.Height = 20;
						buttonM.VerticalAlignment = VerticalAlignment.Top;
						buttonM.HorizontalAlignment = HorizontalAlignment.Left;


						Button buttonP = new Button();
						buttonP.Margin = new Thickness(170, 90, 10, 10);
						buttonP.Name = "PlusOption";
						buttonP.Content = "+";
						buttonP.Width = 20;
						buttonP.Height = 20;
						buttonP.VerticalAlignment = VerticalAlignment.Top;
						buttonP.HorizontalAlignment = HorizontalAlignment.Left;
						buttonP.Click += (Ps, Pe) =>
						{
							buttonP.IsEnabled = false;
							answersCount++;

							buttonM.Margin = new Thickness(145, buttonM.Margin.Top + 20, 10, 10);
							buttonP.Margin = new Thickness(170, buttonP.Margin.Top + 20, 10, 10);

							RadioButton radioButton = new RadioButton();
							radioButton.VerticalAlignment = VerticalAlignment.Top;
							radioButton.HorizontalAlignment = HorizontalAlignment.Left;
							radioButton.Margin = new Thickness(145, buttonP.Margin.Top - 20, 10, 10);
							radioButton.Name = string.Format("Option_{0}", answersCount);
							radioButton.Width = 100;
							radioButton.Height = 20;
							TestGrid.Children.Add(radioButton);

							TextBox textBoxAT = new TextBox();
							textBoxAT.Margin = new Thickness(radioButton.Margin.Left + 20, radioButton.Margin.Top, 10, 10);
							textBoxAT.Width = 200;
							textBoxAT.Height = 15;
							textBoxAT.VerticalAlignment = VerticalAlignment.Top;
							textBoxAT.HorizontalAlignment = HorizontalAlignment.Left;
							textBoxAT.Name = string.Format("AnswerText_{0}", answersCount);
							TestGrid.Children.Add(textBoxAT);


							Button SA = new Button();
							SA.Margin = new Thickness(385, radioButton.Margin.Top, 10, 10);
							SA.Name = string.Format("SA");
							SA.Content = "Сохранить";
							SA.Width = 80;
							SA.Height = 20;
							SA.VerticalAlignment = VerticalAlignment.Top;
							SA.HorizontalAlignment = HorizontalAlignment.Left;
							SA.Click += (r, t) =>
							{
								((RadioButton)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("Option_{0}", answersCount))).Content =
							((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount))).Text;
								TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount)));
								TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("SA")));
								buttonP.IsEnabled = true;

							};
							TestGrid.Children.Add(SA);

						};

						buttonM.Click += (Ms, Me) =>
						{
							if (buttonM.Margin.Top != 90)
							{
								buttonP.IsEnabled = true;


								buttonM.Margin = new Thickness(145, buttonM.Margin.Top - 20, 10, 10);
								buttonP.Margin = new Thickness(170, buttonP.Margin.Top - 20, 10, 10);

								TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount)));
								TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("SA")));

								TestGrid.Children.Remove((RadioButton)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("Option_{0}", answersCount)));
								answersCount--;
							}


						};

						TestGrid.Children.Add(buttonP);
						TestGrid.Children.Add(buttonM);
						Button saveCorrAns = new Button();
						saveCorrAns.Margin = new Thickness(500, 90, 10, 10);
						saveCorrAns.Name = "SaveAnswer";
						saveCorrAns.Content = "Сохранить ответ";
						saveCorrAns.Width = 120;
						saveCorrAns.Height = 30;
						saveCorrAns.HorizontalAlignment = HorizontalAlignment.Left;
						saveCorrAns.VerticalAlignment = VerticalAlignment.Top;
						saveCorrAns.Click += (SAs, SAe) =>
						{
							string ans = "";
							string ansText = "";
							foreach (var a in TestGrid.Children)
							{
								if (a is RadioButton)
								{
									ansText += ((RadioButton)a).Content + ":;:";
								}
								if (a is RadioButton && ((RadioButton)a).IsChecked == true)
								{
									ans = ((RadioButton)a).Name.Remove(0, 7);
								}
							}
							if (ansText != "" && ansText != null)
								ansText = ansText.Remove(ansText.Length - 3, 3);
							questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine = ans;
							questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AllAnswers = ansText;
							RemoveAll();

						};
						TestGrid.Children.Add(saveCorrAns);
					}
					

				}
				if (questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerTypeID == 3)
				{
					if (questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine != "" &&
					questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine != null)
					{
						string[] CAIanswers = questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AllAnswers
							.Split(new string[] { ":;:" }, StringSplitOptions.None);
						string[] Canswers = questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine
							.Split(new string[] { ";;" }, StringSplitOptions.None);
						
						for (int j = 0; j < CAIanswers.Length; j++)
						{
							CheckBox rbut = new CheckBox();
							string answers = 
							rbut.Name = string.Format("BoxOption_{0}", j + 1);
							if (Canswers.Contains((j+1).ToString()))
							{
								rbut.IsChecked = true;
							}
							rbut.Margin = new Thickness(150, 90 + (10 * (j * 2)), 10, 10);
							rbut.Width = 150;
							rbut.Height = 30;
							rbut.Content = CAIanswers[j];
							rbut.HorizontalAlignment = HorizontalAlignment.Left;
							rbut.VerticalAlignment = VerticalAlignment.Top;
							TestGrid.Children.Add(rbut);

						}
						int answersCount = CAIanswers.Length;

						Button buttonM = new Button();
						buttonM.Margin = new Thickness(145, 90 + (answersCount * 2 * 10), 10, 10);
						buttonM.Name = "MinusOption";
						buttonM.Content = "-";
						buttonM.Width = 20;
						buttonM.Height = 20;
						buttonM.VerticalAlignment = VerticalAlignment.Top;
						buttonM.HorizontalAlignment = HorizontalAlignment.Left;


						Button buttonP = new Button();
						buttonP.Margin = new Thickness(170, 90 + (answersCount * 2 * 10), 10, 10);
						buttonP.Name = "PlusOption";
						buttonP.Content = "+";
						buttonP.Width = 20;
						buttonP.Height = 20;
						buttonP.VerticalAlignment = VerticalAlignment.Top;
						buttonP.HorizontalAlignment = HorizontalAlignment.Left;
						buttonP.Click += (Ps, Pe) =>
						{
							buttonP.IsEnabled = false;
							answersCount++;

							buttonM.Margin = new Thickness(145, buttonM.Margin.Top + 20, 10, 10);
							buttonP.Margin = new Thickness(170, buttonP.Margin.Top + 20, 10, 10);

							CheckBox radioButton = new CheckBox();
							radioButton.VerticalAlignment = VerticalAlignment.Top;
							radioButton.HorizontalAlignment = HorizontalAlignment.Left;
							radioButton.Margin = new Thickness(145, buttonP.Margin.Top - 20, 10, 10);
							radioButton.Name = string.Format("BoxOption_{0}", answersCount);
							radioButton.Width = 100;
							radioButton.Height = 20;
							TestGrid.Children.Add(radioButton);

							TextBox textBoxAT = new TextBox();
							textBoxAT.Margin = new Thickness(radioButton.Margin.Left + 20, radioButton.Margin.Top, 10, 10);
							textBoxAT.Width = 200;
							textBoxAT.Height = 15;
							textBoxAT.VerticalAlignment = VerticalAlignment.Top;
							textBoxAT.HorizontalAlignment = HorizontalAlignment.Left;
							textBoxAT.Name = string.Format("AnswerText_{0}", answersCount);
							TestGrid.Children.Add(textBoxAT);


							Button SA = new Button();
							SA.Margin = new Thickness(385, radioButton.Margin.Top, 10, 10);
							SA.Name = string.Format("SA");
							SA.Content = "Сохранить";
							SA.Width = 80;
							SA.Height = 20;
							SA.VerticalAlignment = VerticalAlignment.Top;
							SA.HorizontalAlignment = HorizontalAlignment.Left;
							SA.Click += (r, t) =>
							{
								((CheckBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("BoxOption_{0}", answersCount))).Content =
							((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount))).Text;
								TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount)));
								TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("SA")));
								buttonP.IsEnabled = true;

							};
							TestGrid.Children.Add(SA);

						};

						buttonM.Click += (Ms, Me) =>
						{
							if (buttonM.Margin.Top > 90)
							{
								buttonP.IsEnabled = true;


								buttonM.Margin = new Thickness(145, buttonM.Margin.Top - 20, 10, 10);
								buttonP.Margin = new Thickness(170, buttonP.Margin.Top - 20, 10, 10);

								TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount)));
								TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("SA")));

								TestGrid.Children.Remove((CheckBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("BoxOption_{0}", answersCount)));
								answersCount--;
							}


						};

						TestGrid.Children.Add(buttonP);
						TestGrid.Children.Add(buttonM);
						Button saveCorrAns = new Button();
						saveCorrAns.Margin = new Thickness(500, 90, 10, 10);
						saveCorrAns.Name = "SaveAnswer";
						saveCorrAns.Content = "Сохранить ответ";
						saveCorrAns.Width = 120;
						saveCorrAns.Height = 30;
						saveCorrAns.HorizontalAlignment = HorizontalAlignment.Left;
						saveCorrAns.VerticalAlignment = VerticalAlignment.Top;
						saveCorrAns.Click += (SAs, SAe) =>
						{
							string ans = "";
							string ansText = "";
							foreach (var a in TestGrid.Children)
							{
								if (a is CheckBox)
								{
									ansText += ((CheckBox)a).Content + ":;:";
								}
								if (a is CheckBox && ((CheckBox)a).IsChecked == true)
								{
									ans += ((CheckBox)a).Name.Remove(0, 10) + ";;";
								}
							}
							if (ansText != "" && ansText != null)
								ansText = ansText.Remove(ansText.Length - 3, 3);
							if (ans != "" && ans != null)
								ans = ans.Remove(ans.Length - 2, 2);
							questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine = ans;
							questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AllAnswers = ansText;
							RemoveAll();

						};
						TestGrid.Children.Add(saveCorrAns);
					}
					else
					{
						int answersCount = 0;

						Button buttonM = new Button();
						buttonM.Margin = new Thickness(145, 90, 10, 10);
						buttonM.Name = "MinusOption";
						buttonM.Content = "-";
						buttonM.Width = 20;
						buttonM.Height = 20;
						buttonM.VerticalAlignment = VerticalAlignment.Top;
						buttonM.HorizontalAlignment = HorizontalAlignment.Left;


						Button buttonP = new Button();
						buttonP.Margin = new Thickness(170, 90, 10, 10);
						buttonP.Name = "PlusOption";
						buttonP.Content = "+";
						buttonP.Width = 20;
						buttonP.Height = 20;
						buttonP.VerticalAlignment = VerticalAlignment.Top;
						buttonP.HorizontalAlignment = HorizontalAlignment.Left;
						buttonP.Click += (Ps, Pe) =>
						{
							buttonP.IsEnabled = false;
							answersCount++;

							buttonM.Margin = new Thickness(145, buttonM.Margin.Top + 20, 10, 10);
							buttonP.Margin = new Thickness(170, buttonP.Margin.Top + 20, 10, 10);

							CheckBox radioButton = new CheckBox();
							radioButton.VerticalAlignment = VerticalAlignment.Top;
							radioButton.HorizontalAlignment = HorizontalAlignment.Left;
							radioButton.Margin = new Thickness(145, buttonP.Margin.Top - 20, 10, 10);
							radioButton.Name = string.Format("BoxOption_{0}", answersCount);
							radioButton.Width = 100;
							radioButton.Height = 20;
							TestGrid.Children.Add(radioButton);

							TextBox textBoxAT = new TextBox();
							textBoxAT.Margin = new Thickness(radioButton.Margin.Left + 20, radioButton.Margin.Top, 10, 10);
							textBoxAT.Width = 200;
							textBoxAT.Height = 15;
							textBoxAT.VerticalAlignment = VerticalAlignment.Top;
							textBoxAT.HorizontalAlignment = HorizontalAlignment.Left;
							textBoxAT.Name = string.Format("AnswerText_{0}", answersCount);
							TestGrid.Children.Add(textBoxAT);


							Button SA = new Button();
							SA.Margin = new Thickness(385, radioButton.Margin.Top, 10, 10);
							SA.Name = string.Format("SA");
							SA.Content = "Сохранить";
							SA.Width = 80;
							SA.Height = 20;
							SA.VerticalAlignment = VerticalAlignment.Top;
							SA.HorizontalAlignment = HorizontalAlignment.Left;
							SA.Click += (r, t) =>
							{
								((CheckBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("BoxOption_{0}", answersCount))).Content =
							((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount))).Text;
								TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount)));
								TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("SA")));
								buttonP.IsEnabled = true;

							};
							TestGrid.Children.Add(SA);

						};

						buttonM.Click += (Ms, Me) =>
						{
							if (buttonM.Margin.Top != 90)
							{
								buttonP.IsEnabled = true;


								buttonM.Margin = new Thickness(145, buttonM.Margin.Top - 20, 10, 10);
								buttonP.Margin = new Thickness(170, buttonP.Margin.Top - 20, 10, 10);

								TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", answersCount)));
								TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("SA")));

								TestGrid.Children.Remove((CheckBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("BoxOption_{0}", answersCount)));
								answersCount--;
							}


						};

						TestGrid.Children.Add(buttonP);
						TestGrid.Children.Add(buttonM);
						Button saveCorrAns = new Button();
						saveCorrAns.Margin = new Thickness(500, 90, 10, 10);
						saveCorrAns.Name = "SaveAnswer";
						saveCorrAns.Content = "Сохранить ответ";
						saveCorrAns.Width = 120;
						saveCorrAns.Height = 30;
						saveCorrAns.HorizontalAlignment = HorizontalAlignment.Left;
						saveCorrAns.VerticalAlignment = VerticalAlignment.Top;
						saveCorrAns.Click += (SAs, SAe) =>
						{
							string ans = "";
							string ansText = "";
							foreach (var a in TestGrid.Children)
							{
								if (a is CheckBox)
								{
									ansText += ((CheckBox)a).Content + ":;:";
								}
								if (a is CheckBox && ((CheckBox)a).IsChecked == true)
								{
									ans += ((CheckBox)a).Name.Remove(0, 10) + ";;";
								}
							}
							if (ansText != "" && ansText != null)
								ansText = ansText.Remove(ansText.Length - 3, 3);
							if (ans != "" && ans != null)
								ans = ans.Remove(ans.Length - 2, 2);
							questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AnswerLine = ans;
							questionList[Convert.ToInt32(button.Name.Remove(0, 9))].AllAnswers = ansText;
							RemoveAll();

						};
						TestGrid.Children.Add(saveCorrAns);
					}
				}
			};

			TestGrid.Children.Add(button);

			AddButton.IsEnabled = false;

			TextBox textBox = new TextBox();
			textBox.Margin = new Thickness(145, 70, 10, 10);
			textBox.Width = 200;
			textBox.Height = 20;
			textBox.HorizontalAlignment = HorizontalAlignment.Left;
			textBox.VerticalAlignment = VerticalAlignment.Top;
			textBox.Name = "QuestionText";
			textBox.Text = "Введите сюда вопрос";
			textBox.PreviewTextInput += (Ts, Te) =>
			{
				if(textBox.Text == "Введите сюда вопрос")
				{
					textBox.Text = "";
				}
			};
			TestGrid.Children.Add(textBox);

			Button button1 = new Button();
			button1.Margin = new Thickness(360, 70, 10, 10);
			button1.Name = "SaveQuestionArgs";
			button1.Content = "Сохранить вопрос";
			button1.Width = 120;
			button1.Height = 30;
			button1.HorizontalAlignment = HorizontalAlignment.Left;
			button1.VerticalAlignment = VerticalAlignment.Top;
			TestGrid.Children.Add(button1);

			string questionText = "";
			int ansType = 0;
			button1.Click += (Qs, Qe) => 
			{
				questionText = ((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, "QuestionText")).Text;
				TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, "QuestionText"));
				TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, "SaveQuestionArgs"));

				ComboBox comboBox = new ComboBox();
				comboBox.Margin = new Thickness(145, 70, 10, 10);
				comboBox.Width = 200;
				comboBox.Height = 20;

				comboBox.Items.Add("Ответ - Строка");
				comboBox.Items.Add("Ответ - Один из вариантов");
				comboBox.Items.Add("Ответ - Несколько вариантов");
				comboBox.Name = "AnswerTypeCB";
				comboBox.HorizontalAlignment = HorizontalAlignment.Left;
				comboBox.VerticalAlignment = VerticalAlignment.Top;
				TestGrid.Children.Add(comboBox);

				Button button2 = new Button();
				button2.Margin = new Thickness(360, 70, 10, 10);
				button2.Name = "SaveQuestionArgs";
				button2.Content = "Сохранить вопрос";
				button2.Width = 120;
				button2.Height = 30;
				button2.HorizontalAlignment = HorizontalAlignment.Left;
				button2.VerticalAlignment = VerticalAlignment.Top;
				TestGrid.Children.Add(button2);
				button2.Click += (ATs, ATe) =>
				{
					ansType = ((ComboBox)LogicalTreeHelper.FindLogicalNode(TestGrid, "AnswerTypeCB")).SelectedIndex + 1;
					questionList.Add(new Question { QuestionText = questionText, AnswerTypeID = ansType });
					button.IsEnabled = true;
					AddButton.IsEnabled = true;
					TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, "SaveQuestionArgs"));
					TestGrid.Children.Remove((ComboBox)LogicalTreeHelper.FindLogicalNode(TestGrid, "AnswerTypeCB"));

				};

			};

			
		}

		
		private void textBox_PreviewTextInput(object sender, TextCompositionEventArgs e)
		{
			if (!Char.IsDigit(e.Text, 0))
			{
				e.Handled = true;
			}
		}

		private void RemoveAll()
		{
			TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, "SaveQuestionArgs"));
			TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("Question_{0}", questionList.Count)));
			TestGrid.Children.Remove((ComboBox)LogicalTreeHelper.FindLogicalNode(TestGrid, "AnswerTypeCB"));
			TestGrid.Children.Remove((Label)LogicalTreeHelper.FindLogicalNode(TestGrid, "qLabel"));
			TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, "AnswerLine"));
			TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, "QuestionText"));
			TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, "SaveAnswer"));
			TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, "PlusOption"));
			TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, "MinusOption"));
			TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, "Option_"));
			for (int j = 0; j < 20; j++)
			{
				TestGrid.Children.Remove((RadioButton)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("Option_{0}", j)));
				TestGrid.Children.Remove((CheckBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("BoxOption_{0}", j)));
				TestGrid.Children.Remove((TextBox)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("AnswerText_{0}", j)));
				
			}
		}

		private void DelButton_Click(object sender, RoutedEventArgs e)
		{
			if(questionList.Count != 0)
			{
				questionList.RemoveAt(questionList.Count - 1);
				TestGrid.Children.Remove((Button)LogicalTreeHelper.FindLogicalNode(TestGrid, string.Format("Question_{0}", questionList.Count)));
			}
		}

		private void Exit_Click(object sender, RoutedEventArgs e)
		{
			Window window = new Redacting(user);
			window.Show();
			this.Close();
		}

		private void Create_Click(object sender, RoutedEventArgs e)
		{
			if(questionList.Count > 0)
			{
				using (TestSystemEntities db = new TestSystemEntities())
				{
					string questionsLine = "";
					foreach (Question a in questionList)
					{
						db.Question.Add(a);
						db.SaveChanges();
						
						foreach (Question b in db.Question)
						{
							if (a == b)
							{
								questionsLine += (b.ID + ";;").ToString();
							}
							
						}
						
						
					}
					if ((from s in db.Tests where s.TestName == testName.Text select s) != null)
					{
						MessageBox.Show("Такое название уже существует!!!", "Ошибка", MessageBoxButton.OK, MessageBoxImage.Error);

					}
					else
					{
						questionsLine = questionsLine.Remove(questionsLine.Length - 2, 2);
						db.Questions.Add(new Questions { QustionsLine = questionsLine });
						db.Tests.Add(new Tests
						{
							TestName = testName.Text,
							QuestionsID = (from s in db.Questions where s.QustionsLine == questionsLine select s.ID).FirstOrDefault()
						});
						db.SaveChanges();
						Window window = new Redacting(user);
						window.Show();
						this.Close();
					}
					
				}
				
			}
			else MessageBox.Show("Создайте хотя бы один вопрос", "Ошибка", MessageBoxButton.OK, MessageBoxImage.Error);
		}
	}
}

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

namespace WpfApp1
{
	/// <summary>
	/// Логика взаимодействия для Redacting.xaml
	/// </summary>
	public partial class Redacting : Window
	{
		Users user = new Users();
		List<string> testsName = new List<string>();
		public Redacting(Users _user)
		{
			InitializeComponent();
			user = _user;
			_name.Content = user.Name;
			_surname.Content = user.Surname;
			if (user.isTeacher)
			{
				_isTeacher.Content = "Учитель";
			}
			else _isTeacher.Content = "Ученик";
			GetTests();
			foreach (var k in testsName)
				Tests.Items.Add(k);
		}
		void GetTests()
		{
			using (TestSystemEntities db = new TestSystemEntities())
			{
				foreach (var k in from s in db.Tests select s.TestName)
					testsName.Add(k);
			}
		}

		private void Exit_Click(object sender, RoutedEventArgs e)
		{
			Window window = new BaseWindow(user);
			window.Show();
			this.Close();
		}

		private void Redacting_Click(object sender, RoutedEventArgs e)
		{
			if(Tests.SelectedIndex != -1)
			{
				Tests selectedTest = new Tests();
				using (TestSystemEntities db = new TestSystemEntities())
				{
					foreach (Tests t in db.Tests)
					{
						if (t.TestName == Tests.Items[Tests.SelectedIndex].ToString())
						{
							selectedTest = t;
						}
					}
				}
				Window window = new RedactTest(user, selectedTest);
				window.Show();
				this.Close();
			}
			
		}

		private void NewTest_Click(object sender, RoutedEventArgs e)
		{
			Window window = new NewTest(user);
			window.Show();
			this.Close();
		}
	}
}

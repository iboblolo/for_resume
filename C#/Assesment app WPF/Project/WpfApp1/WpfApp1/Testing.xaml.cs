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
	/// Логика взаимодействия для Testing.xaml
	/// </summary>
	public partial class Testing : Window
	{
		List<string> testsName = new List<string>();
		Users user = new Users();
		public Testing(Users _user)
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

		private void Exit_Click(object sender, RoutedEventArgs e)
		{
			Window window = new BaseWindow(user);
			window.Show();
			this.Close();
		}
		void GetTests()
		{
			using (TestSystemEntities db = new TestSystemEntities())
			{
				 foreach (var k in from s in db.Tests select s.TestName)
					testsName.Add(k);
			}
		}
		Tests FindTest(string testName)
		{
			using (TestSystemEntities db = new TestSystemEntities())
			{
				return (from s in db.Tests where s.TestName == testName select s).FirstOrDefault();
			}
		}
		private void StartTest_Click(object sender, RoutedEventArgs e)
		{
			if (Tests.SelectedIndex != -1)
			{
				Window window = new Test(user, FindTest(Tests.SelectedItem.ToString()));
				window.Show();
				this.Close();
			}
			else MessageBox.Show("Выберите тест","Ошибка",MessageBoxButton.OK, MessageBoxImage.Error);
		}
	}

	
}

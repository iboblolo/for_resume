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
using static System.Net.Mime.MediaTypeNames;

namespace WpfApp1
{
	/// <summary>
	/// Логика взаимодействия для Results.xaml
	/// </summary>
	public partial class Results : Window
	{
		Users user = new Users();
		public Results(Users _user)
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

			GetResults();
		}

		private void Exit_Click(object sender, RoutedEventArgs e)
		{
			Window window = new BaseWindow(user);
			window.Show();
			this.Close();
		}

		void GetResults()
		{
			CompletedTests c = new CompletedTests();
			
			using (TestSystemEntities db = new TestSystemEntities())
			{
				foreach (var k in from s in db.CompletedTests where user.ID == s.UserCompID select s)
					ResultsList.Items.Add(string.Format("Тест: {0}\t Результат: {1}", k.Tests.TestName, k.Result));
			}
		}
	}
}

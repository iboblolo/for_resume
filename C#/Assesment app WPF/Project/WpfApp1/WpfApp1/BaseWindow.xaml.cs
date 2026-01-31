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
	/// Логика взаимодействия для BaseWindow.xaml
	/// </summary>
	public partial class BaseWindow : Window
	{
		Users user = new Users();
		public BaseWindow(Users _user)
		{
			InitializeComponent();
			user = _user;
			_name.Content = user.Name;
			_surname.Content = user.Surname;
			if (user.isTeacher)
			{
				_isTeacher.Content = "Учитель";
				Redact.IsEnabled = true;
			}
			else _isTeacher.Content = "Ученик";
		}

		private void ExitButton_Click(object sender, RoutedEventArgs e)
		{
			Window window = new MainWindow();
			window.Show();
			this.Close();
		}

		private void Testing_Click(object sender, RoutedEventArgs e)
		{
			Window window = new Testing(user);
			window.Show();
			this.Close();
		}

		private void Results_Click(object sender, RoutedEventArgs e)
		{
			Window window = new Results(user);
			window.Show();
			this.Close();
		}

		private void Redact_Click(object sender, RoutedEventArgs e)
		{
			Window window = new Redacting(user);
			window.Show();
			this.Close();
		}
	}
}

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
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Data.Entity;
using System.Data.Entity.Core.Objects;
using System.Runtime.InteropServices;
using System.Runtime.Remoting.Contexts;
using System.Security.Policy;
using System.Threading;
using System.Security.Cryptography;
using System.Windows.Controls.Primitives;
using System.Data.SqlTypes;
using System.Xml.Linq;
using System.Diagnostics;


namespace WpfApp1
{
	/// <summary>
	/// Логика взаимодействия для MainWindow.xaml
	/// </summary>
	public partial class MainWindow : Window
	{
		public MainWindow()
		{
			InitializeComponent();
		}

		private void Enter_Click(object sender, RoutedEventArgs e)
		{
			if (Log.Text != "" && Pass.Text != "")
			{
				if (Enter(Log.Text, Pass.Text))
				{
					Window window = new BaseWindow(FindUser(Log.Text, Pass.Text));
					window.Show();
					this.Close();
				}
			}
			else MessageBox.Show("Введите все данные", "Ошибка", MessageBoxButton.OK, MessageBoxImage.Error);
		}

		private void Registration_Click(object sender, RoutedEventArgs e)
		{
			Window Reg = new RegistrationForm();
			Reg.Show();
		}
		Users FindUser(string login, string password)
		{
			using (TestSystemEntities db = new TestSystemEntities())
			{
				return (from s in db.Users where s.Login == login && s.Password == password select s).FirstOrDefault();
			}
		}
		static bool Enter(string login, string password)
		{
			using (TestSystemEntities db = new TestSystemEntities())
			{
				if ((from s in db.Users where s.Login == login select s).FirstOrDefault() != null)
				{
					if ((from s in db.Users where s.Login == login && s.Password == password select s).FirstOrDefault() != null)
					{
						return true;
					}
					else
					{
						MessageBox.Show("Пароль не верный", "Ошибка", MessageBoxButton.OK, MessageBoxImage.Error);
						return false;
					}
				}
				else
				{
					MessageBox.Show("Такого логина нет", "Ошибка", MessageBoxButton.OK, MessageBoxImage.Error);
					return false;
				}
			}
		}

		private void Exit_CLick(object sender, RoutedEventArgs e)
		{
			this.Close();
		}
	}
}

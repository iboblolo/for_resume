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
using System.ComponentModel.Design;
using System.Runtime.CompilerServices;

namespace WpfApp1
{
	/// <summary>
	/// Логика взаимодействия для RegistrationForm.xaml
	/// </summary>
	public partial class RegistrationForm : Window
	{
		public RegistrationForm()
		{
			InitializeComponent();
		}

		private void Button_Click(object sender, RoutedEventArgs e)
		{
			if (Log.Text != "" && Pass.Text != "" && Fname.Text != "" && Sname.Text != "" && isTeach.SelectedIndex != -1)
			{
				bool teach;
				if (isTeach.SelectedIndex == 1) teach = true;
				else teach = false;
				Users user = new Users { Login = Log.Text, Password = Pass.Text, Name = Fname.Text, Surname = Sname.Text, isTeacher =  teach};
				Registration(user);
				this.Close();
			}
			else
			{
				MessageBox.Show("Введите все данные", "Ошибка", MessageBoxButton.OK, MessageBoxImage.Error);
			}
        }
		async static void Registration(Users user)
		{
			using (TestSystemEntities db = new TestSystemEntities())
			{
				if (await (from s in db.Users where s.Login == user.Login select s).FirstOrDefaultAsync() == null)
				{
					db.Users.Add(user);
					await db.SaveChangesAsync();
				}
				else
				{
					MessageBox.Show("Такой логин уже существует", "Ошибка", MessageBoxButton.OK, MessageBoxImage.Error);
				}
			}
		}
		
	}
}

using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.EntityFrameworkCore;
using EXAM_ASP;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using EXAM_ASP.Models;
using Microsoft.AspNetCore.Authorization;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddDbContext<TestSystemEntities>(options =>
	options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddIdentity<Users, IdentityRole>(options =>
{
	options.User.RequireUniqueEmail = false;
})
	.AddEntityFrameworkStores<TestSystemEntities>()
	.AddDefaultTokenProviders();

builder.Services.AddTransient<IAuthorizationHandler, TeacherHandler>();

builder.Services.AddAuthorization((options) =>
{
    options.AddPolicy("OnlyForTeachers",
        policy =>
        {
            policy.Requirements.Add(new TeacherRequirement(true));
        });
});

builder.Services.AddControllersWithViews();



var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
	app.UseExceptionHandler("/Home/Error");
	// The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
	app.UseHsts();
}



app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthentication();
app.UseAuthorization();

app.MapControllerRoute(
	name: "default",
	pattern: "{controller=Home}/{action=Index}");

app.Run();

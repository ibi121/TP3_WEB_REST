using Microsoft.AspNetCore.Mvc;
using System;

namespace LivreurCSharp.Controller
{
    public class IndexController : Controller
    {
        [HttpGet]
        public IActionResult ObtenirRoute()
        {
            return View();
        }
    }
}
